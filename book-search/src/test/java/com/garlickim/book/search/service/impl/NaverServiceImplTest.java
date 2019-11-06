package com.garlickim.book.search.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverServiceImplTest
{

    @Autowired
    NaverServiceImpl naverService;





    @Test
    public void getUrl()
    {
        Assert.assertEquals("https://openapi.naver.com/v1/search/book_adv.json?", this.naverService.getUrl());
    }





    @Test
    public void getQueryParameter()
    {
        Assert.assertEquals("d_titl=%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0&start=1",
                            this.naverService.getQueryParameter(BookSearch.builder()
                                                                          .keyword("자료구조")
                                                                          .type("1")
                                                                          .page(1)
                                                                          .build()));
    }





    @Test
    public void setProperty()
    {
        try
        {
            URL url = new URL("");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            this.naverService.setProperty(connection);

            Assert.assertEquals("2GNd6CCIbCzgAJt7MGWF", connection.getRequestProperty("X-Naver-Client-Id"));
            Assert.assertEquals("hOqmSBFivh", connection.getRequestProperty("X-Naver-Client-Secret"));
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }





    @Test
    public void convertBook()
    {
        String str = "{\"lastBuildDate\": \"Wed, 06 Nov 2019 19:55:11 +0900\",\"total\": 5,\"start\": 1,\"display\": 5,\"items\": [{\"title\": \"<b>여행 기획자</b>가 될테야\",\"link\": \"http://book.naver.com/bookdb/book_detail.php?bid=6336209\",\"image\": \"https://bookthumb-phinf.pstatic.net/cover/063/362/06336209.jpg?type=m1&udate=20190105\",\"author\": \"이장원\",\"price\": \"12000\",\"discount\": \"10800\",\"publisher\": \"여원미디어\",\"pubdate\": \"20190301\",\"isbn\": \"8961685791 9788961685795\",\"description\": \"“꿈은 미래를 향한 소중한 첫걸음!”\\n“미래 사회의 변화와 직업의 세계!”\\n\\n「탄탄 미래직업 속으로」는 직업에 관한 다양한 정보뿐만 아니라 실제로 직업인들이 일하는 모습과 과정, 그리고 일하면서 겪는 힘든 점과 즐거움을 흥미롭게 보여줍니다. 또 자기가 좋아하는 일을 하는 것이 얼마나 중요한지도 알려... \"},{\"title\": \"앙코르와트, 지금 이 순간 (어느 <b>여행</b>상품 <b>기획자</b>의 이야기)\",\"link\": \"http://book.naver.com/bookdb/book_detail.php?bid=9595591\",\"image\": \"https://bookthumb-phinf.pstatic.net/cover/095/955/09595591.jpg?type=m1&udate=20181115\",\"author\": \"김문환\",\"price\": \"16000\",\"discount\": \"14400\",\"publisher\": \"이담북스\",\"pubdate\": \"20151005\",\"isbn\": \"892687088X 9788926870884\",\"description\": \"이 책은 <b>여행</b>사에서 근무하는 <b>여행</b>상품<b>기획자</b>가 5,200시간 동안 앙코르와트와 연락, 방문하면서 알게 된 내용을 자세하게 소개하고 있어 ‘앙코르와트’ <b>여행</b>자에게 필요한 가장 실질적 정보를 담고 있다. 앙코르와트 <b>여행</b>을 위해 필요한 기본 준비사항부터 앙코르와트에 가서는 꼭 보아야 할 유적지 소개와... \"},{\"title\": \"개념<b>여행</b> (<b>여행기획자</b> 정란수가 말하는 착한 여행, 나쁜 여행)\",\"link\": \"http://book.naver.com/bookdb/book_detail.php?bid=6898134\",\"image\": \"https://bookthumb-phinf.pstatic.net/cover/068/981/06898134.jpg?type=m1&udate=20150724\",\"author\": \"정란수\",\"price\": \"13800\",\"discount\": \"12420\",\"publisher\": \"시대의창\",\"pubdate\": \"20120515\",\"isbn\": \"8959402362 9788959402366\",\"description\": \"우리와 타인의 삶 모두가 행복한 <b>여행</b>!『개념 <b>여행</b>』은 관광 개발과 <b>여행</b> 기획 관련 컨설팅을 하고 있는 저자 정란수가 일상의 개념 <b>여행</b>을 제안한 책이다. ‘개념 <b>여행</b>’ 이란 사회를 지속시킬 수 있는 <b>여행</b>을 뜻하는 것으로, 지역사회의 ‘삶의 질’ 향상, 방문객에게 ‘양질의 관광 경험’ 제공, 지역사회와... \"},{\"title\": \"IT로켓011 나는 게임<b>기획자</b>다 II 게임문화를 <b>여행</b>하는 히치하이커를 위한 안내서\",\"link\": \"http://book.naver.com/bookdb/book_detail.php?bid=14295381\",\"image\": \"https://bookthumb-phinf.pstatic.net/cover/142/953/14295381.jpg?type=m1&udate=20190102\",\"author\": \"황선혜\",\"price\": \"9900\",\"discount\": \"\",\"publisher\": \"테마<b>여행</b>신문TTNThemeTravelNewsKorea\",\"pubdate\": \"20181117\",\"isbn\": \"1158844328 9791158844325\",\"description\": \"시스템 <b>기획자</b> 17호 소개 05-2. 모바일과 PC MMORPG에서의 시스템 기획 05-3. 프로그래밍 지식은 필수적인가? 05-4. 퀘스트 기획 05-5.... IT로켓(IT Rocket) 도서목록(011) 테마<b>여행</b>신문 TTN Korea 도서목록(530)  테마<b>여행</b>신문 TTN Theme Travel News Korea editor@themetn.com 웹진... \"},{\"title\": \"앙코르와트, 지금 이 순간 (<b>여행</b>상품<b>기획자</b>가 추천하는 솔직담백 캄보디아 <b>여행</b>)\",\"link\": \"http://book.naver.com/bookdb/book_detail.php?bid=7381938\",\"image\": \"https://bookthumb-phinf.pstatic.net/cover/073/819/07381938.jpg?type=m1&udate=20150715\",\"author\": \"김문환\",\"price\": \"16000\",\"discount\": \"\",\"publisher\": \"이담북스\",\"pubdate\": \"20131209\",\"isbn\": \"8926853667 9788926853665\",\"description\": \"캄보디아 상품기획담당자로 캄보디아 현지와 소통하고 출장을 통해 남다른 경험을 하면서 솔직하게 캄보디아 <b>여행</b>정보를 제공하고 있다. 유적지에 대한 이론적인 상세한 내용보다는 실속 있게 <b>여행</b>을 준비하는법과 캄보디아 현지의 이야기로 서술되어 있어 <b>여행</b>자에게 유용한 정보를 제공해 줄 것이다.\"}]}";

        Document document = this.naverService.convertBook(str);
        List<String> list = document.getBooks()
                                    .stream()
                                    .map(book -> book.getTitle())
                                    .collect(Collectors.toList());

        Assert.assertTrue(list.contains("여행 기획자가 될테야"));
        Assert.assertNotNull(document.getBooks());
        Assert.assertNotNull(document.getTotalCnt());
        Assert.assertNotNull(document.getPageableCnt());

    }





    @Test
    public void searchBook()
    {
        Document document = this.naverService.searchBook(BookSearch.builder()
                                                                   .keyword("자료구조")
                                                                   .type("1")
                                                                   .page(1)
                                                                   .build());

        Assert.assertNotNull(document.getBooks());
    }
}
