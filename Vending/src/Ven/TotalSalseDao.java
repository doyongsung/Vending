package Ven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TotalSalseDao {
   
   //싱글톤 패턴
     private TotalSalseDao() {
     }
     
     //클래스 내부에 인스턴스
     static private TotalSalseDao dao = new TotalSalseDao();
     
     //메소드를 통해 반환
     public static TotalSalseDao getInstance() {
        return dao;
     }
     
     
     //전체 타입 검색기능
     //반환타입 List<productList>
     //매개변수 - Connection 객체 : Statement
     ArrayList<TotalList> gettTotalList(Connection conn){
        
        ArrayList<TotalList> list = null;
       
        // 데이터 베이스의 ProductList 테이블 이용 select 결과물 -> list 에 저장
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
         stmt = conn.createStatement();
         String sql = "select p.itemcode,p.name, b.buyqty, b.totalprice from PRODUCTINFO p, BUYINFO b "
         		+ "where p.itemcode=b.itemcode order by b.totalprice desc";
         
         //결과 받아오기
         rs = stmt.executeQuery(sql);
         
         list = new ArrayList<>();
         
         //데이터를 ProductList 객체로 생성 -> list에 저장
         
         while(rs.next()) {
        	 TotalList PL = new TotalList(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
            
            list.add(PL);
         }

      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
         if(rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
         if(stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
        
      return list;
      

      }
     
     //2. 입력받은 상품만 출력
     int insertProductList(Connection conn, String name) {
        
        int result = 0;
        PreparedStatement pstmt = null;
        
        try {
           String sql =
                 "select p.itemcode,p.name, b.buyqty, b.totalprice from PRODUCTINFO p, BUYINFO b where p.itemcode=b.itemcode and p.name = ?";
           pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, name);
           
           
        result = pstmt.executeUpdate();
           
        } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
        } finally {
           if(pstmt != null) {
              try {
                 pstmt.close();
              } catch (SQLException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
              }
           }
        }
        return result;
     }
}