//정수를 입력받아 테이블에서 해당 데이터를 삭제

package oracleuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class OracleDelete {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Scanner sc = new Scanner(System.in);
		System.out.print("부서 번호 입력: ");
		int deptno = sc.nextInt();
		sc.close();
		String sql = "delete from dept where deptno = ?";
		try {
			// 드라이버 클래스 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 데이터베이스 연결
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			
			//autocommit 해제
			con.setAutoCommit(false);
			
			// SQL 실행객체 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			int r = pstmt.executeUpdate();
			if (r > 0) {
				System.out.println("삭제 성공");
				// 작업에 성공하면 commit을 호출
				con.commit();
			} else {
				// 조건에 맞는 데이터가 없으면 실패하는 게 아니라 삭제하는 행의 개수가 0인 것.
				// 실패하면 예외 발생이므로 catch로 감.
				System.out.println("조건에 맞는 데이터가 없습니다.");
			}
		} catch (Exception e) {
			//작업도중 예외가 발생한 경우 rollback을 호출
			try {
				con.rollback();
			} catch (Exception e1) {
			}
			// 예외의 내용을 출력
			System.out.println(e.getMessage());
			// 예외가 발생한 지점을 역추적
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
	}
}
