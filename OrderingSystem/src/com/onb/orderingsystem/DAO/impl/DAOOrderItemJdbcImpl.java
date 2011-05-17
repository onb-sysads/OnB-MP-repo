import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrderItem;
import com.onb.orderingsystem.domain.*;

public class DAOOrderItemJdbcImpl implements DAOOrderItem {

	private final static String INSERTANORDERITEM = "INSERT INTO ORDERITEM (ORDERITEM_QTY, ORDERITEM_PRODUCT, ORDERITEM_ORDERID, ORDERITEM_PRICE) VALUES (?,?,?,?)";
	private final static String GETALLORDERITEM = "SELECT * FROM ORDERITEM WHERE ORDERS.ORDER_ID = ORDERITEM.ORDERITEM_ORDERID AND ORDERITEM.ORDERITEM_PRODUCT = PRODUCT.PRODUCT_ID AND CUSTOMER.CUSTOMER_ID = ORDERS.ORDER_CUSTOMER";
	private final static String GETANORDERITEM = GETALLORDERITEM
			+ " AND ORDERITEM.ORDERITEM_ORDERID =? AND ORDERITEM.ORDERITEM_PRODUCT = ?";
	private final static String DELETEANORDERITEM = "delete from ORDERITEM where ORDERITEM.ORDERITEM_PRODUCT = ? AND ORDERITEM.ORDERITEM_ORDERID = ?";

	private final static String JDBCURL = "jdbc:mysql://localhost:3306/ORDERINGSYSTEM_TEST";
	private final static String JDBCUSER = "root";
	private final static String JDBCPASSWD = "";
	private final static String JDBCDRIVER = "com.mysql.jdbc.Driver";

	private Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ORDERINGSYSTEM_TEST", "root", "");
		return con;
	}

	@Override
	public List<OrderItem> getAll() throws DAOException, SQLException {

		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		OrderItem orderItem;

		Connection conn = getConnection();

		int orderitem_qty = 0;
		int product_productId;
		BigDecimal orderItemPrice = BigDecimal.ZERO;

		try {

			PreparedStatement pstmt = conn.prepareStatement(GETALLORDERITEM);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				orderitem_qty = rs.getInt("ORDERITEM_QTY");
				orderItemPrice = rs.getBigDecimal("ORDERITEM_PRICE");
				product_productId = rs.getInt("PRODUCT_ID");
				Product product = new Product(product_productId);
				Order order = new Order(rs.getInt("ORDER_ID"));
				orderItem = new OrderItem(order, product, orderitem_qty);
				orderItemList.add(orderItem);
			}

		} catch (SQLException s) {
			System.out.println("SQL statement is not executed!" + " " + s);
		}

		return orderItemList;
	}

	@Override
	public void update(OrderItem oi) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(OrderItem orderItem) throws DAOException, SQLException {

		// OrderItem orderItem = null;
		Connection conn = getConnection();

		try {

			PreparedStatement pstmt = conn.prepareStatement(INSERTANORDERITEM);
			pstmt.setInt(1, orderItem.getOrderItemQuantity());
			pstmt.setInt(2, orderItem.getOrderItemProduct().getProductID());
			pstmt.setInt(3, orderItem.getOrderItemOrder().getOrderID());
			pstmt.setBigDecimal(4, orderItem.getOrderItemTotalPrice());
			pstmt.execute();

		} catch (SQLException s) {
			System.out.println("SQL statement is not executed!" + " " + s);
		}

	}

	@Override
	public void delete(OrderItem orderItem) throws DAOException, SQLException {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(DELETEANORDERITEM);
			pstmt.setInt(1, orderItem.getOrderItemProduct().getProductID());
			pstmt.setInt(2, orderItem.getOrderItemOrder().getOrderID());

			pstmt.execute();
			System.out.println("dumman dito");

		} catch (SQLException s) {
			System.out.println("SQL statement is not executed!" + " " + s);
		}

	}

	@Override
	public OrderItem findOrderItem(int orderId, int productId)
			throws DAOException, SQLException {
		OrderItem orderItem = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int orderitem_qty = 0;
		int product_productId;

		try {

			pstmt = conn.prepareStatement(GETANORDERITEM);
			pstmt.setInt(1, orderId);
			pstmt.setInt(2, productId);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				orderitem_qty = rs.getInt("ORDERITEM_QTY");

				product_productId = rs.getInt("PRODUCT_ID");
				Product product = new Product(product_productId);
				Order order = new Order(rs.getInt("ORDER_ID"));

				orderItem = new OrderItem(order, product, orderitem_qty);
			}

		} catch (SQLException s) {
			System.out.println("SQL statement is not executed!" + " " + s);
		}

		return orderItem;
	}

}
