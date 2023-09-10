// src/service/loginAPI.js
import axios from "axios"

const getUserInfo = (userId, userPw) => {
    const reqData = {
        'email': userId,
        'password': userPw
    }


    return axios.post('api/v1/login', reqData, {
        headers: {
            'Content-type': 'application/json'
        }
    })
}

export default {
    async doLogin(userId, userPw) {
        try {
            const getUserInfoPromise = getUserInfo(userId, userPw)
            const [userInfoResponse] = await Promise.all([getUserInfoPromise])
            if (userInfoResponse.data.length === 0) {
                return 'notFound'
            } else {
                localStorage.setItem('user_token', userInfoResponse.data.accessToken)
                //LocalStorage.setItem('user_role', userInfoResponse.data.role)
                return userInfoResponse
            }
        } catch (err) {
            console.error(err)
        }
    }
}