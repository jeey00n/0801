package oracleuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class OracleDataBinding {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		// 키보드로부터 입력받을 수 있는 객체 생성
		Scanner sc = new Scanner(System.in);

		System.out.print("부서 번호: ");
		int deptno = sc.nextInt();

		System.out.print("부서 이름: ");
		// 이전에 남아있던 Enter를 제거하기 위한 코드
		sc.nextLine();
		String dname = sc.nextLine();

		System.out.print("지역: ");
		String loc = sc.nextLine();

		sc.close();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			String sql = "insert into dept(deptno, dname, loc) values(?, ?, ?)";
			// 다른 데이터를 바인딩할수있는 PreparedStatement 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			pstmt.setString(2, dname);
			pstmt.setString(3, loc);
			int r = pstmt.executeUpdate();
			if (r > 0) {
				System.out.println("삽입 성공");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}
}
