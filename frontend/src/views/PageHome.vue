<template>
  <div>
    <PageBanner />
    <div class="container mt-3">
      <div class="card-container-wrapper">
        <div v-for="(category) in categories" :key="category.name" class="card-container">
          <div class="card">
            <h3>{{ category.name }}</h3>
            <hr>
            <div class="card-deck">
              <div v-for="post in category.posts" :key="post.postId" class="card-item">
                <div class="card-body">
                  <div class="title-wrapper">
                    <span v-if="post.title.length < 10"><strong>{{ post.title }}</strong> &nbsp;&nbsp;
                      <i class="fa-solid fa-comment small-icon">{{ post.replyCount }}</i>
                      <i class="fa-solid fa-heart small-icon">{{ post.likeCount }}</i>
                    </span>
                    <span v-else><strong>{{ post.title.substring(0, 10) + "..." }}</strong>
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
    }
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
  background-color: #d0f3c7;
  border: 1px solid #ddd;
  border-radius: 5px;
  width: 100%; /* 카드의 너비를 100%로 설정 */
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.6);
  margin: 15px; /* 간격 추가 */
}

/* 카드 아이템 스타일 */
.card-item {
  margin: 5px;
  width: 100%; /* 카드 아이템의 너비를 100%로 설정 */
  border: 3px solid #92bb92; /* 카드 아이템에 테두리 추가 */
}

/* 카드 아이템 제목 정렬 스타일 */
.title-wrapper {
  text-align: left;
}
</style>
