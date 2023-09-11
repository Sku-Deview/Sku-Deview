<template>
    <br><br>
    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <p>&copy; 2023 Your Company. All rights reserved.</p>
                </div>
                <div class="col-md-6 text-md-right">
                    <a href="#">Privacy Policy</a> | <a href="#">Terms of Service</a>
                </div>
            </div>
        </div>
    </footer>
</template>

<script>
export default {}
</script>

<style scoped>
/* 푸터 스타일링 */
.footer {
    background-color: lightgray; /* 배경색 설정 */
    color: black; /* 글자색 설정 */
    padding: 20px 0; /* 상하 여백 설정 */
}

.footer a {
    color: black; /* 링크 색상 설정 */
    text-decoration: none; /* 밑줄 제거 */
    margin-left: 10px; /* 링크 사이 간격 설정 */
}

.footer a:hover {
    text-decoration: underline; /* 호버 시 밑줄 표시 */
}
</style>
