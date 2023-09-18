<template>
    <div class="m-black-bg" v-if="modalOpen === true">
        <div class="m-white-bg">
            <b-button @click="modalOpen = false" class="m-modal-exit-btn ">
                닫기
            </b-button>
            <h1>{{ modalList.title }}</h1>

            <div class="info-row">
                <div class="info">
                    <span class="m-sendName">보낸사람 :{{ modalList.senderName }}</span>
                    <span class="create-at">&nbsp&nbsp 날짜: {{ formatDateTime(modalList.regDate) }}</span>
                </div>
            </div>

            <hr>
            <h2>{{ modalList.content }}</h2>

            <b-button @click="[modalOpen = false,toMessageWrite(modalList.senderName)]" class="m-modal-exit-btn button-button">
                답장
            </b-button>
        </div>
    </div>

    <div class="board-list mt-5">

        <table class="w3-table-all">
            <thead>
            <tr>
                <th>No</th>
                <th>받는 사람</th>
                <th>제목</th>
                <th>보낸 사람</th>

            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, idx) in list" :key="idx">
                <td>{{ idx }}</td>
                <td>{{ item.receiverName }}</td>
                <td @click="modalOpen = true">
                    <b-button v-if="item.title.length<20" v-on:click="clickModel(item)">{{ item.title }}</b-button>
                    <b-button v-else v-on:click="clickModel(item)">{{ item.title.substring(0, 20) + "..." }}</b-button>
                </td>
                <td>{{ item.senderName }}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    data() {
        return {
            list: {},
            modalOpen: false,
            modalList: {},
        }
    },
    mounted() {
        this.ReceiveListGet()
    },
    methods: {
        formatDateTime(dateTime) {
            const date = new Date(dateTime);
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const day = date.getDate().toString().padStart(2, '0');
            const hours = date.getHours().toString().padStart(2, '0');
            const minutes = date.getMinutes().toString().padStart(2, '0');

            return `${year}-${month}-${day} ${hours}:${minutes}`;
        },

        ReceiveListGet() {
            this.$axios.get("/api/v1/message/received", {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('user_token')}`
                }
            })
                .then((res) => {
                    this.list = res.data.data
                }).catch((err) => {
                if (err.message.indexOf('Network Error') > -1) {
                    alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
            })
        },
        clickModel(item) {
            this.modalList = item
        },

        toMessageWrite(receiverNickname) {
            this.$router.push({
                path: '/message/write',
                query: {name: receiverNickname}
            })
        },
    }
}
</script>

<style>
.m-sendName {
    color: #ff2b1d;
}

.receiverName {
    color: #204bff;
}

.m-black-bg {
    display: flex;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.432);
    position: fixed;
    padding: 5px;
}

.m-white-bg {
    width: 60%;
    height: 50%;
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: left; /* 왼쪽 정렬로 변경 */
}

.m-button-container {
    text-align: right; /* 버튼을 오른쪽 정렬 */
}

.m-modal-exit-btn {
    margin-top: 0; /* 마진 값 제거 */
    margin-right: 0; /* 마진 값 제거 */
    float: right; /* 버튼을 오른쪽으로 띄움 */
}

.m-button-button {
    position: absolute;
    bottom: 20px; /* 하단과의 간격 조절 */
    right: 20px; /* 오른쪽과의 간격 조절 */
}

.m-modal-exit-btn:hover {
    cursor: pointer;
}
</style>