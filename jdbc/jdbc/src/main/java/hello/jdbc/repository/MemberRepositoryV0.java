package hello.jdbc.repository;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0 {
    
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";
        
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);   // DB애 전달할 SQL과 파라미터로 전달할 데이터들을 준비
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2,member.getMoney());
            pstmt.executeUpdate();               // 준비된 쿼리가 실행됨
            return member;
        }catch (SQLException e){
            log.error("db error", e);
            throw e;
        } finally {
            // 항상 실행 해줘야함
            close(con,pstmt, null);
        }

    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) { // 무조건 호출 필요
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt(("money")));
                return member;
            }else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        }catch (SQLException e){
            log.info("DB error",e);
            throw e;
        }finally {
            close(con, pstmt, rs);
        }

    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet  rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);   // DB애 전달할 SQL과 파라미터로 전달할 데이터들을 준비
            pstmt.setInt(1, money);
            pstmt.setString(2,memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}",resultSize);

        }catch (SQLException e){
            log.error("db error", e);
            throw e;
        } finally {
            // 항상 실행 해줘야함
            close(con,pstmt, null);
        }

    }
    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet  rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);   // DB애 전달할 SQL과 파라미터로 전달할 데이터들을 준비
            pstmt.setString(1,memberId);
            pstmt.executeUpdate();
        }catch (SQLException e){
            log.error("db error", e);
            throw e;
        } finally {
            // 항상 실행 해줘야함
            close(con,pstmt, null);
        }
    }

    private void close(Connection con , Statement stmt, ResultSet rs){
        //ResultSet : 결과 조회
        if (rs != null){
            try {
                rs.close();       //SQLException
            } catch (SQLException e) {
                log.info("error",e);
            }
        }

        // 시작과 역순으로 close
        if (stmt != null) {
            try {
                stmt.close();       //SQLException
            } catch (SQLException e) {
                log.info("error",e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error",e);
            }
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
