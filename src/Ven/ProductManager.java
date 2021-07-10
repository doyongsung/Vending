package Ven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductManager {
	private ProductListDao dao;
	private Scanner sc;

	Connection conn = null;
	String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "hr";
	String pw = "tiger";

	public ProductManager(ProductListDao dao) {
		this.dao = dao;
		sc = new Scanner(System.in);
	}

	// 관리자모드
	void SaleSelect() {

		CoinSearch coin = new CoinSearch(CoinDAO.getInstance());
		SalesAmount sales = new SalesAmount(TotalSalseDao.getInstance());
		Order order = new Order(ProductListDao.getInstance());
		MainSelect main = new MainSelect();

		Scanner sc = new Scanner(System.in);

		try {
			System.out.println("———————————————————관리자 모드—————————————————————");
			System.out.println();
			System.out.println(" 1. 음료 재고 목록 조회");
			System.out.println(" 2. 자판기 내 잔액 조회");
			System.out.println(" 3. 음료 판매량 조회");
			System.out.println(" 4. 음료 발주");
			System.out.println(" 5. 뒤로 가기");
			System.out.println();
			System.out.println("————————————————————————————————————————————————");
			System.out.println("원하시는 기능의 번호를 입력해주세요.");
			int num = Integer.parseInt(sc.nextLine());
			System.out.println();

			switch (num) {
			case 1:
				saleShowinfo();
				break;
			case 2:
				coin.CoinList();
				break;
			case 3:
				sales.totalList();
				break;
			case 4:
				itemList();
				order.order();
				return;
			case 5:
				main.main();
				break;
			}
		} catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다.");
		} catch (IllegalStateException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}

	// 관리자> 재고목록조회
	void saleShowinfo() {
		ProductManager manager = new ProductManager(ProductListDao.getInstance());
		Order order = new Order(ProductListDao.getInstance());
		Scanner sc = new Scanner(System.in);

		while (true) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");

				System.out.println();
				System.out.println("———————————————————관리자 모드—————————————————————");
				manager.itemList();
				System.out.println(" 1.음료 추가");
				System.out.println(" 2.음료 수정");
				System.out.println(" 3.음료 삭제");
				System.out.println(" 4.뒤로 가기");
				System.out.println(" 5.프로그램 종료");
				System.out.println("————————————————————————————————————————————————");
				System.out.println("원하시는 기능의 번호를 입력해주세요.");
				int num = sc.nextInt();

				switch (num) {
				case 1:
					manager.inputData();
					break;
				case 2:
					manager.update();
					break;
				case 3:
					manager.delproduct();
					break;
				case 4:
					SaleSelect();
					break;
				case 5:
					System.out.println("프로그램을 정상적으로 종료합니다.");
					System.exit(0);
				}

			} catch (InputMismatchException e) {
				System.out.println("잘못된입력입니다. 다시입력해주세요");
				sc = new Scanner(System.in);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 음료 리스트
	void itemList() {

		try {
			conn = DriverManager.getConnection(jdbcUrl, user, pw);
			List<ProductList> list = dao.getProductList(conn);

			System.out.println("——————————————————— 음료 리스트 ———————————————————");
			System.out.println("————————————————————————————————————————————————");
			System.out.println("음료번호 \t 음료이름 \t\t 음료가격 \t\t 재고");
			System.out.println("————————————————————————————————————————————————");

			for (ProductList pl : list) {
				System.out.printf("  %d \t %s \t\t %d \t\t %d \n", pl.getItemcode(), pl.getName(), pl.getPrice(),
						pl.getItemQty());
			}
			System.out.println("————————————————————————————————————————————————");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 음료 추가
	void inputData() {

		try {
			conn = DriverManager.getConnection(jdbcUrl, user, pw);

			System.out.println("음료 정보를 입력합니다.");
			System.out.println("음료이름 음료가격 재고 음료번호 순으로 입력해주세요");
			System.out.println("ex) 음료수 2000 20 ");
			String inputData = sc.nextLine();
			String[] deptdata = inputData.split(" ");

			ProductList pList = new ProductList(0, deptdata[0], Integer.parseInt(deptdata[1]),
					Integer.parseInt(deptdata[2]));

			int result = dao.insertProductList(conn, pList);

			if (result > 0) {
				System.out.println("입력되었습니다.");
			} else {
				System.out.println("입력 실패");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("잘못된정보입니다. 다시입력해주세요");
		}
	}

	// 음료 수정
	void update() {

		try {
			conn = DriverManager.getConnection(jdbcUrl, user, pw);
			try {
				conn = DriverManager.getConnection(jdbcUrl, user, pw);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("음료 정보를 수정합니다.");
			System.out.println("음료번호 음료이름 음료가격 재고 순으로 입력해주세요");
			String editData = sc.nextLine();
			String[] eData = editData.split(" ");

			ProductList productList = new ProductList(Integer.parseInt(eData[0]), eData[1], Integer.parseInt(eData[2]),
					Integer.parseInt(eData[3]));

			int result = dao.updateProductList(conn, productList);

			if (result > 0) {
				System.out.println("수정되었습니다.");
			} else {
				System.out.println("수정실패!!!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("잘못된정보입니다. 다시입력해주세요");
		}
	}

	// 음료 삭제
	void delproduct() {

		try {
			conn = DriverManager.getConnection(jdbcUrl, user, pw);
			itemList();
			System.out.println("삭제를 원하시는 음료 번호를 입력해주세요.");
			int productList = Integer.parseInt(sc.nextLine());
			int result = dao.deleteProduct(conn, productList);

			if (result > 0) {
				System.out.println("삭제되었습니다.");
			} else {
				System.out.println("해당 부서의 정보가 없습니다.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("잘못된정보입니다. 다시입력해주세요");
		}
	}

}