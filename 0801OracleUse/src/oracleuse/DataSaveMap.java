//dept테이블의 모든 데이터를 Map의 List로 저장하기

package oracleuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataSaveMap {

	public static void main(String[] args) {
		// DB에서 데이터 읽기를 위한 변수
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 읽어온 데이터 저장을 위한 자료구조
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			pstmt = con.prepareStatement("select deptno, dname, loc from dept");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 하나의 행을 저장할 map객체를 생성
				Map<String, Object> map = new HashMap<>();
				map.put("deptno", rs.getInt("deptno"));
				map.put("dname", rs.getString("dname"));
				map.put("loc", rs.getString("loc"));
				// 읽은 하나의 행을 리스트에 저장
				list.add(map);
			}
			System.out.printf("%10s", "부서번호");
			System.out.printf("%20s", "부서이름");
			System.out.printf("%30s", "부서위치");
			System.out.println();
			for (Map map : list) {
				System.out.printf("%9s", map.get("deptno"));
				System.out.printf("%15s", map.get("dname"));
				System.out.printf("%20s", map.get("loc"));
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
	}
}
