package ticketManager.databaseAccessLayer;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.TicketModel;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseTicketContext implements ITicketContext {
    private MysqlDataSource dataSource;
    public DatabaseTicketContext()  {
        dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setPort(3307);
        dataSource.setDatabaseName("ticketSystem");
        dataSource.setServerName("localhost");
        try {
            dataSource.setServerTimezone("UTC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TicketModel increaseAmountAndGetById(String id) {

        try{
            Connection conn = dataSource.getConnection();
            conn.setAutoCommit(false);


            PreparedStatement update = conn.prepareStatement("UPDATE ticket SET count = count + 1 WHERE ticketNumber = ?");
            update.setString(1, id);

            PreparedStatement select = conn.prepareStatement("SELECT * FROM ticket WHERE ticketNumber = ?");
            select.setString(1, id);

            update.execute();

            TicketModel model = new TicketModel();
            ResultSet result = select.executeQuery();
            try {
                if (result.next()) {
                    model.setId(result.getInt("id"));
                    model.setAmountChecked(result.getInt("count"));
                    model.setDate(result.getDate("created"));
                    return model;
                }
            }finally {
                conn.commit();
                conn.close();
            }
        }catch (SQLException ignored){
            ignored.printStackTrace();
        }
        return null;
    }

    public TicketModel[] getAllAfterId(int id) {
        ArrayList<TicketModel> models = new ArrayList<TicketModel>();
        try{
            Connection conn = dataSource.getConnection();

            PreparedStatement select = conn.prepareStatement("SELECT * FROM ticket WHERE id > ?");
            select.setInt(1, id);
            ResultSet found = select.executeQuery();
            if (found.next()) {
                TicketModel ticket = new TicketModel();

                ticket.setId(found.getInt("id"));
                ticket.setAmountChecked(found.getInt("count"));
                ticket.setDate(found.getDate("created"));
                models.add(ticket);
            }

            conn.close();

        }catch (SQLException ignored){
            ignored.printStackTrace();
        }
        return models.toArray(new TicketModel[models.size()]);
    }

    public boolean postTicket(TicketModel ticket) {
        return false;
    }

    public TicketModel[] getAllValidAfterId(int id) {
        return null;
    }

    public TicketModel createTicket(TicketModel model) {
        try{
            Connection conn = dataSource.getConnection();

            String query="INSERT INTO `ticket` (`id`, `ticketNumber`, `count`, `created`) VALUES (NULL, 0, '0', ?);";
            //String query="Insert INTO Table_A(name, age) ('abc','123' )";//Doesn't escape
            PreparedStatement prest;
            prest = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // prest.setString(1,model.getTicketNumber());
            prest.setTimestamp(1, new Timestamp(model.getDate().getTime()));
            prest.executeUpdate();
            ResultSet rs = prest.getGeneratedKeys();
            rs.next();
            model.setId( rs.getInt(1) );
            prest.close();

            String updateQuery="UPDATE `ticket` SET ticketNumber = ? WHERE id = ?";
            PreparedStatement updatePrest = conn.prepareStatement(updateQuery);
            updatePrest.setString(1, model.getTicketNumber());
            updatePrest.setInt(2, model.getId());
            updatePrest.execute();
            updatePrest.close();
            conn.close();

        }catch (SQLException ignored){
            ignored.printStackTrace();
        }
        return model;
    }
}
