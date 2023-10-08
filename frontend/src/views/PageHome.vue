<template>
  <div>
    <PageBanner/>
    <div class="container mt-3">
      <br><h2>안녕하세요! <strong>SK-DEVIEW</strong> 에 방문하신 것을 환영합니다! 😄</h2><br><br>
      <div class="card-container-wrapper">
        <div v-for="(category) in categories" :key="category.name" class="card-container">
          <div class="card">
            <span class="mt-2">
              <span style="font-size: 20px"><strong>{{ category.name }}</strong></span>
              <span class="ml-auto mouse-cursor" @click="fnSelectCategory(category.name)">
                <i class="fa-solid fa-angle-right small-icon"></i> <!-- 오른쪽 화살표 아이콘 추가 -->
              </span>
              <hr>
            </span>

            <div class="card-deck">
              <div v-for="post in category.posts" :key="post.postId" class="card-item hover-pointer" @click="fnView(post.postId)">
                <div class="card-body">
                  <div>
                    <span v-if="post.title.length < 10">
                      <strong>{{ post.title }}</strong>
                      <i class="fa-solid fa-comment small-icon">{{ post.replyCount }}</i>
                      <i class="fa-solid fa-heart small-icon">{{ post.likeCount }}</i>
                    </span>
                    <span v-else>
                      <strong>{{ post.title.substring(0, 10) + "..." }}</strong>
                      <i class="fa-solid fa-comment small-icon">{{ post.replyCount }}</i>
                      <i class="fa-solid fa-heart small-icon">{{ post.likeCount }}</i>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PageBanner from "@/components/PageBanner.vue";

export default {
  components: {
    PageBanner
  },
  data() {
    return {
      requestBody: {}, // 리스트 페이지 데이터 전송,
      categories: [
        {
          name: "공지사항",
          posts: [],
        },
        {
          name: "질문",
          posts: [],
        },
        {
          name: "정보",
          posts: [],
        },
        {
          name: "자유",
          posts: [],
        },
      ],
    };
  },
  created() {
    // 각 카테고리 데이터를 가져오기 위한 함수 호출
    this.getCategoryData("NOTICE", 0);
    this.getCategoryData("QNA", 1);
    this.getCategoryData("INFO", 2);
    this.getCategoryData("FREE", 3);
  },
  methods: {
    getCategoryData(categoryName, index) {
      if (categoryName === "NOTICE") {
        // NOTICE 카테고리 데이터를 가져오기 위한 API 호출
        this.$axios.get(`/api/v1/posts/notice`, {
          params: {
            size: 10 // 최근 10개만 가져오도록 설정
          }
        })
            .then((response) => {
              // 데이터를 카테고리에 할당
              this.categories[index].posts = response.data.data;
            })
            .catch((error) => {
              console.error(error);
            });
      } else {
        // 다른 카테고리 데이터를 가져오기 위한 API 호출
        this.$axios.get(`/api/v1/posts`, {
          params: {
            postCategory: categoryName,
            size: 10 // 최근 10개만 가져오도록 설정
          }
        })
            .then((response) => {
              // 데이터를 카테고리에 할당
              this.categories[index].posts = response.data.data.content;
            })
            .catch((error) => {
              console.error(error);
            });
      }
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: 'board/detail',
        query: {idx}
      })
    },
    fnSelectCategory(categoryName) {
      let queryCategory;
      switch (categoryName) {
        case "공지사항":
          queryCategory = "NOTICE";
          break;
        case "질문":
          queryCategory = "QNA";
          break;
        case "정보":
          queryCategory = "INFO";
          break;
        case "자유":
          queryCategory = "FREE";
          break;
        default:
          queryCategory = ""; // 다른 경우에는 빈 문자열로 처리
      }
      // 해당 카테고리로 이동하기 위해 라우터를 사용합니다.
      this.$router.push({path: 'board/list', query: {postCategory: queryCategory}});
    },
  }
};
</script>

<style scoped>
/* 바깥쪽 컨테이너 스타일 */
.card-container-wrapper {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between; /* 가로 간격을 최대화하여 2개의 컨테이너가 들어가도록 설정 */
  gap: 10px; /* 간격을 10px로 설정 */
}

/* 내부 컨테이너 스타일 */
.card-container {
  flex-basis: calc(45% - 5px); /* 가로로 2개의 컨테이너가 들어가도록 설정 (간격을 고려하여 반씩 차지) */
}

/* 카드 스타일 */
.card {
  background-color: ghostwhite;
  border: 1px solid #ddd;
  border-radius: 5px;
  width: 100%;
}

.small-icon {
  font-size: 15px; /* Adjust the font size to your preference */
}

.hover-pointer:hover {
  background-color: lightgray; /* 마우스 호버 시 원하는 배경색으로 변경하세요. */
}

.mouse-cursor {
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
}
</style>
