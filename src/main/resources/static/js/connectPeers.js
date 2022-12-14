// 영상이 들어갈 공간
const showings = document.getElementsByClassName('showing');
// showingsCount 0번째는 본인이 들어갈 위치. 1번째부터 상대방 영상.
// var showingsCount = 1;
// 임시로 영상을 추가할때 사용됨(실험용)
const testgrid = document.getElementById('test_grid');

const myVideo = document.createElement('video')
myVideo.style.width = '100%';
myVideo.style.height = '100%';

const room_name = document.getElementById('room_name').value;

const socket2 = io('wss://192.168.10.97:3000', {
    transports: ['websocket']
})
var tagTest;

navigator.mediaDevices.getUserMedia({
    video: true,
    audio: true
}).then(async (stream) => {
    const myPeer = new Peer({
        config: {
            'iceServers': [
                { url: 'stun:stun.xten.com' },
            ]
        }
    }, {
        host: '/',
        port: 3001,
        secure: false
    })

    // peer start.
    myPeer.on('open', id => {
        console.log(`${room_name}방 참가중`, id)
        socket2.emit('join-room', room_name, id)
    })

    await addVideoStream(myVideo, stream, showings[0])

    myPeer.on('call', call => {
        console.log('기존 참가자 불러오는 중..');
        call.answer(stream)

        // 접속 시 다른사람의 스트림(영상, 소리)를 받아옵니다.
        call.on('stream', async (receivedStream) => {
            // console.log('receivedStream', showingsCount, receivedStream)
            const video = document.createElement('video')
            video.style.width = '100%';
            video.style.height = '100%';
            tagTest = video;

            // 처음보는 스트림(사용자의 영상)이라면
            if (streamIdList.indexOf(receivedStream.id) == -1) {
                streamIdList.push(receivedStream.id)
                for(let showingsCount = 1; showings[showingsCount]; showingsCount++) {
                    // 비디오 태그가 없다면
                    if (!showings[showingsCount].querySelector('video')) {
                        await addVideoStream(video, receivedStream, showings[showingsCount])
                        break;
                    }
                }
                // await addVideoStream(video, receivedStream, testgrid)
            }
        })
    })

    // 들어온 사람과 통신을 시도합니다.
    socket2.on('user-connected', async userId => {
        console.log('새로운 참가자 연결됨', userId)
        const call = await myPeer.call(userId, stream, { metadata: { userId: peers.id } })
        console.log('새로운 참가자 들어오는중..')
        const video = document.createElement('video')
        video.style.width = '100%';
        video.style.height = '100%';

        call.on('stream', async(userVideoStream) => {
            // console.log('userVideoStream', userVideoStream, streamIdList.indexOf(userVideoStream.id))

            // 처음보는 스트림(사용자의 영상)이라면
            if (streamIdList.indexOf(userVideoStream.id) == -1) {
                console.log('참가자 스트림 받는중/')
                streamIdList.push(userVideoStream.id)
                for(let showingsCount = 1; showings[showingsCount]; showingsCount++) {
                    // 비디오 태그가 없다면
                    if (!showings[showingsCount].querySelector('video')) {
                        await addVideoStream(video, userVideoStream, showings[showingsCount])
                        break;
                    }
                }
                // await addVideoStream(video, userVideoStream, testgrid)
            } else {
                console.log('streamIdList에 추가되지않은 스트림', userVideoStream)
            }
        })
        call.on('close', () => {
            console.log('사용자가 나갔습니다.')
            video.remove()
        })
        peers[userId] = call
    })
})

socket2.on('user-disconnected', userId => {
    console.log('userDisconnected', userId)
    if (peers[userId]) {
        peers[userId].close()
    }
})

myVideo.muted = true
const peers = {}

// //쓰이지않음 (복구용)
// function connectToNewUser(myPeer, userId, stream) {
//     // console.log('connectToNewUser', userId)
//     const call = myPeer.call(userId, stream, { metadata: { userId: peers.id } })
//     console.log('새로운 참가자 들어오는중..')
//     const video = document.createElement('video')
//     video.style.width = '200px';
//     video.style.height = '200px';

//     call.on('stream', async (userVideoStream) => {
//         console.log('새로운 참가자 id', userVideoStream)
//         if (streamIdList.indexOf(stream.id) == -1) {   // 처음보는 스트림(사용자의 영상)이라면
//             console.log('참가자 스트림 받는중/', showingsCount, '번에 배치됨.')
//             streamIdList.push(stream.id)
//             // await addVideoStream(video.cloneNode(), userVideoStream, showings[showingsCount++])
//             await addVideoStream(video, userVideoStream, testgrid)
//         } else {
//             console.log('배치되지 않은 유저', userVideoStream)
//         }
//     })
//     call.on('close', () => {
//         console.log('close')
//         video.remove()
//     })
//     peers[userId] = call
// }

var streamIdList = []
async function addVideoStream(video, stream, grid) {
    // console.log('addVideoStream', showingsCount, stream);
    // console.log('stream added:', streamIdList)
    video.srcObject = stream
    video.addEventListener('loadedmetadata', async () => {
        video.play()
    })
    // console.log('append video', video)
    grid.append(video)
}