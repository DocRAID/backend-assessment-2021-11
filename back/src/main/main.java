package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import java.awt.*;

public class main {

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver"; 

    public static void main(String[] args) {
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        Scanner in = new Scanner(System.in);

        File file = new File("data/index.html");
        
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            

            Class.forName(JDBC_DRIVER);

            // MariaDB내의 menus 데이터베이스로 접근
            String url = "jdbc:mariadb://mariadb.codingstudy.kr:3306/std_user13"; 
            String id = "std_user13";
            String pw = "1234";
            conn = DriverManager.getConnection(url, id, pw);
            
            for(int i=1;i<10;i++) System.out.println(".");
            System.out.println("DB 연결 성공");
            
            // css는 외부 파일로 저장하여 사용
            // html 작성 후 데이터베이스를 연동하여 나타낼 부분 전까지 복사·붙여넣기
            
            bw.write("<!DOCTYPE html>\r\n"
            		+ "<html lang=\"en\">\r\n"
            		+ "<head>\r\n"
            		+ "  <meta charset=\"UTF-8\">\r\n"
            		+ "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
            		+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
            		+ "  <title>rentalCar</title>\r\n"
            		+ "  <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\" />\r\n"
            		+ "  \r\n"
            		+ "  <style>\r\n"
            		+ "    * {\r\n"
            		+ "      margin: 0;\r\n"
            		+ "      padding: 0;\r\n"
            		+ "      \r\n"
            		+ "    }\r\n"
            		+ "    /* 화살 애니메 */\r\n"
            		+ "    @keyframes updown { \r\n"
            		+ "        0% {margin-top: 0px;}\r\n"
            		+ "	      50% {margin-top: 7px;}\r\n"
            		+ "        100% {margin-top: 0px;}\r\n"
            		+ "    }\r\n"
            		+ "    .material-icons {\r\n"
            		+ "      font-size: 25px;\r\n"
            		+ "      color: #445dd3;\r\n"
            		+ "    }\r\n"
            		+ "    #map {\r\n"
            		+ "      margin-top: 90px;\r\n"
            		+ "      position: absolute;\r\n"
            		+ "      height: 90%;\r\n"
            		+ "      width: 80%;\r\n"
            		+ "      border-radius: 30px;\r\n"
            		+ "    }\r\n"
            		+ "    .container {\r\n"
            		+ "      position: absolute;\r\n"
            		+ "      height: 99.73%;\r\n"
            		+ "      width: 20%;\r\n"
            		+ "      right: 0;\r\n"
            		+ "      background-color: #fff;\r\n"
            		+ "    }\r\n"
            		+ "    #company-Name {\r\n"
            		+ "      font-size: 900;\r\n"
            		+ "      padding: 20px;\r\n"
            		+ "      text-align: center;\r\n"
            		+ "    }\r\n"
            		+ "    .inner {\r\n"
            		+ "      padding-left: 30px;\r\n"
            		+ "      margin-bottom: 20px;\r\n"
            		+ "    }\r\n"
            		+ "    .inner p {\r\n"
            		+ "      font-size: 900;\r\n"
            		+ "      padding: 10px;\r\n"
            		+ "    }\r\n"
            		+ "    .scroll {\r\n"
            		+ "        overflow: scroll;\r\n"
            		+ "    }\r\n"
            		+ "    .animate{\r\n"
            		+ "        position:absolute;\r\n"
            		+ "        animation: updown 2s infinite;\r\n"
            		+ "    }\r\n"
            		+ "    .logo{\r\n"
            		+ "      position: absolute;\r\n"
            		+ "      width: 270px;\r\n"
            		+ "      margin-top: 5px;\r\n"
            		+ "      /* 로고 높이 변경 */\r\n"
            		+ "    }\r\n"
            		+ "    h2{\r\n"
            		+ "      font-size: 20px;\r\n"
            		+ "    }\r\n"
            		+ "\r\n"
            		+ "    .card {\r\n"
            		+ "      width: 100%;\r\n"
            		+ "      background-color: #eee;\r\n"
            		+ "      box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);\r\n"
            		+ "      transition: 0.3s;\r\n"
            		+ "      margin-bottom: 30px;\r\n"
            		+ "      border-radius: 20px;\r\n"
            		+ "    }\r\n"
            		+ "    .card:hover {\r\n"
            		+ "      box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);\r\n"
            		+ "      background-color: #aaaadd;\r\n"
            		+ "    }\r\n"
            		+ "    /* 정보를 접었다 필수 있게 한다. */\r\n"
            		+ "    .onoff{\r\n"
            		+ "      display: none;\r\n"
            		+ "    }\r\n"
            		+ "  </style>\r\n"
            		+ "</head>\r\n"
            		+ "<body>\r\n"
            		+ "  <script>\r\n"
            		+ "    //필요해서 선언했음. 딜레이 있어야 하드라 ㅎㅎ \r\n"
            		+ "    function goto(x,y, i){\r\n"
            		+ "      onoffjs(i) //이건 리스트 출력하게\r\n"
            		+ "      map.setLevel(2);setTimeout(function() {panTo(x,y);}, 50);\r\n"
            		+ "    }\r\n"
            		+ "    function onoffjs(i){\r\n"
            		+ "      if(document.getElementsByClassName(\"onoff\")[i].style.display==\"block\"){\r\n"
            		+ "        document.getElementsByClassName(\"onoff\")[i].style.display=\"none\"\r\n"
            		+ "      }\r\n"
            		+ "      else{\r\n"
            		+ "        document.getElementsByClassName(\"onoff\")[i].style.display=\"block\"\r\n"
            		+ "      }\r\n"
            		+ "    }\r\n"
            		+ "  </script>\r\n"
            		+ "  <div id=\"head\">\r\n"
            		+ "    <img class=\"logo\" src=\"./Rant.png\" alt=\"logo\" >\r\n"
            		+ "  </div>\r\n"
            		+ "  <div id=\"map\"></div>\r\n"
            		+ "  <section class=\"container scroll\">\r\n"
            		+ "    <!-- panTo > 좌표를 바꿀수 이씀 ㅇㅇ -->");
            
            
            // SQL 쿼리 실행·작성
            stmt = conn.createStatement();
            System.out.println("지역을 입력하세요");
						String tableName = "2_sorry_i_havent_server";
            String where = in.nextLine();
            
            String sql = "SELECT * FROM " + tableName + " WHERE com_road_address LIKE '%" + where + "%'"; // 지역입력받고 그에 맞는거 찾아야됨
						
            rs = stmt.executeQuery(sql);
            
						bw.write("<h2> <div class=\"material-icons animate\">expand_more</div> &nbsp;&nbsp;&nbsp;&nbsp; \""+where+"\" 검색 목록 결과</h2>\r\n");
            // 쿼리 실행결과를 한 줄씩 받기
						int i=0;
						String strvalue[] = {""};
            while (rs.next()) {
                // 쿼리 실행을 통해 얻은 속성 값을 출력하는데, getString(1)이 첫번째 속성 값이다.
                //System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
                
                // 한줄씩 파일 쓰기작업 실행(html 테이블 내부 행으로 출력)
								// for(int a=1;a<=25;a++){
								// 	if(rs.getString(a)==null){
								// 		Insert
								// 	}
								// }
                bw.write( "<div class=\"card\" onclick=\"goto(" + rs.getString(5) + ", " + rs.getString(6) +", " + i + ")\">\r\n"
                        + "      <h1 id=\"company-Name\" name=\"\">"+ rs.getString(2) +"</h1>\r\n"
                        + "        <div class=\"onoff\">\r\n"
                        + "          \r\n"
                        + "          <div class=\"inner\">\r\n"
                        + "          <h2><div class=\"material-icons\">schedule</div>  영업시간</h2>\r\n"
                        + "          <p name=\"\">평일 영업시간: " + rs.getInt(20) + " ~ " + rs.getInt(21) + "</p>\r\n"
                        + "          <p name=\"\">주말 영업시간: " + rs.getInt(22) + " ~ " + rs.getInt(23) + "</p>\r\n"
                        + "          </div>\r\n"
                        + "          <div class=\"inner\">\r\n"
                        + "          <h2><div class=\"material-icons\">directions_car</div> 차 보유 현황</h2>\r\n"
                        + "          <p name=\"\">총 보유수: " + rs.getInt(10) + "</p>\r\n"
                        + "          <p name=\"\">승용차: " + rs.getInt(11) + "</p>\r\n"
                        + "          <p name=\"\">승합차: " +rs.getInt(12) + "</p>\r\n"
                        + "          <p name=\"\">전기 승용차: " +rs.getInt(13) + "</p>\r\n"
                        + "          <p name=\"\">전기 승합차: " +rs.getInt(14) + "</p> \r\n"
                        + "          <!-- 차 종류 늘림 -->\r\n"
                        + "          </div>\r\n"
                        + "          <div class=\"inner\">\r\n"
                        + "          <h2><div class=\"material-icons\">perm_identity</div>  대표자 정보</h2>\r\n"
                        + "          <p name=\"\">대표자 명: " + rs.getString(24) + "</p>\r\n"
                        + "          <p name=\"\">전화 번호: " +rs.getString(25) + "</p>\r\n"
                        + "          <p name=\"\">사업지 주소: " + rs.getString(3) + "</p>\r\n"
                        + "          </div>\r\n"
                        + "        </div>\r\n"
                        + "       </div>\r\n"
												);
												i++;
						}
						bw.write("  </section>"
						+ "  <script type=\"text/javascript\" src=\"//dapi.kakao.com/v2/maps/sdk.js?appkey=(카카오api키값)\"></script>\r\n"
						+ "  <script>\r\n"
						+ "  //맵 가져오기\r\n"
						+ "\r\n"
						+ "  function panTo(x,y) {\r\n"
						+ "    // 이동할 위도 경도 위치를 생성합니다 \r\n"
						+ "    var moveLatLon = new kakao.maps.LatLng(x,y);\r\n"
						+ "    \r\n"
						+ "    // 지도 중심을 부드럽게 이동시킵니다\r\n"
						+ "    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다\r\n"
						+ "    map.panTo(moveLatLon);            \r\n"
						+ "}        \r\n"
						+ "\r\n"
						+ "    var container = document.getElementById('map'),\r\n"
						+ "    options = {\r\n"
						+ "      //중심좌표\r\n"
						+ "      center: new kakao.maps.LatLng(36.3030465, 128.5848745),\r\n"
						+ "      //확대 레벨\r\n"
						+ "      level: 3\r\n"
						+ "    };\r\n"
						+ " //맵 불러오기\r\n"
						+ "  var map = new kakao.maps.Map(container, options);\r\n"
						+ "  \r\n"
						+ "  // 지도에 표시된 마커 객체를 가지고 있을 배열입니다\r\n"
						+ "  var markers = [];\r\n"
						+ "\r\n"
						+ "  // 마커 생성하는 좌표\r\n");

						rs = stmt.executeQuery(sql);

						while(rs.next()) {
							bw.write("addMarker(new kakao.maps.LatLng(" + rs.getFloat(5) + ", " + rs.getFloat(6) +")); // 좌표 받으면 마크 찍힘***\r\n"
							+ "\r\n"
							+ "  // 마커를 생성하고 지도위에 표시하는 함수입니다\r\n"
							+ "  function addMarker(position) {\r\n"
							+ "    \r\n"
							+ "    // 마커를 생성합니다\r\n"
							+ "    var marker = new kakao.maps.Marker({\r\n"
							+ "      position: position\r\n"
							+ "    });\r\n"
							+ "\r\n"
							+ "    // 마커가 지도 위에 표시되도록 설정합니다\r\n"
							+ "    marker.setMap(map);\r\n"
							+ "      \r\n"
							+ "    // 생성된 마커를 배열에 추가합니다\r\n"
							+ "    markers.push(marker);\r\n"
							+ "  }\r\n");
						}
						bw.write("\r\n"
						+ "</script>\r\n"
						+ "</body>\r\n"
						+ "</html>\r\n"
						+ "\r\n");
            System.out.println("파일 쓰기가 완료되었습니다.");
            
            bw.close();
            osw.close();
            fos.close();
            //파일쓰기 완료 후 생성한 file을 로드
            if(file.exists()) Desktop.getDesktop().open(file);

        } catch (IOException e) {
            System.out.println("파일 입출력 오류");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
