package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Notice;
import dto.QnA;

public class BoardDAO {
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	DBconnectDAO dbcon = new DBconnectDAO();
	
	//noitce 게시글 쓰기
	public void insertNotice(Notice notice){
		dbcon.getCon();
		
		try {
			String sql = "insert into notice (subject, content, signdate, ref, file, writer) values (?,?,?,?,?,?)";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, notice.getSubject());
			pstmt.setString(2, notice.getContent());
			pstmt.setString(3, notice.getSigndate());
			pstmt.setInt(4, notice.getRef());
			pstmt.setString(5, notice.getFile());
			pstmt.setString(6, notice.getWriter());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//noitce 게시글 쓰기 끝
	
	
	//notice 게시판 출력
	public ArrayList<Notice> getAllNotice(int startRow, int endRow){
		dbcon.getCon();
		
		ArrayList<Notice> list = new ArrayList<Notice>();
		
		try {
			String sql = "select * from notice order by signdate desc limit ?,?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Notice notice = new Notice();
				
				notice.setUid(rs.getInt("uid"));
				notice.setSubject(rs.getString("subject"));
				notice.setContent(rs.getString("content"));
				notice.setSigndate(rs.getString("signdate"));
				notice.setRef(rs.getInt("ref"));
				notice.setFile(rs.getString("file"));
				notice.setWriter(rs.getString("writer"));
				
				list.add(notice);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}//notice 게시판 출력 끝
	
	
	//전체게시글 수
	public int getAllcount(){
		dbcon.getCon();
		
		int count = 0;
		
		try {
			String sql = "select count(*) from notice";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();	

			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}//전체게시글 수 끝
	
	
	//한개의 notice게시물
	public Notice oneNotice(int uid) {
		dbcon.getCon();
		
		Notice notice = new Notice();
		
		try {
			String sql = "select * from notice where uid=?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, uid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				notice.setUid(rs.getInt("uid"));
				notice.setSubject(rs.getString("subject"));
				notice.setContent(rs.getString("content"));
				notice.setSigndate(rs.getString("signdate"));
				notice.setRef(rs.getInt("ref"));
				notice.setFile(rs.getString("file"));
				notice.setWriter(rs.getString("writer"));
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notice;
	}//한개의 notice게시물 끝
	
	
	//한개의 notice게시물 삭제
	public void deleteNotice(int uid){
		dbcon.getCon();
		
		try {
			String sql = "delete from notice where uid = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			pstmt.setInt(1, uid);	
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//한개의 notice게시물 삭제 끝
	
	//notice 게시물 수정
	public void modifyNotice(Notice notice){
		dbcon.getCon();
		
		try {
			String sql = "update notice set subject=?, content=?, signdate=?, file=?, writer=?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, notice.getSubject());
			pstmt.setString(2, notice.getContent());
			pstmt.setString(3, notice.getSigndate());
			pstmt.setString(4, notice.getFile());
			pstmt.setString(5, notice.getWriter());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//notice 게시물 수정
	
	
	
	///////////QnA/////////////
	
    // 게시글 가져오기
    public QnA getBoard(int num) {
        dbcon.getCon();
        
        QnA qna = null;
        try {
            String sql = "select * from qna where num = ?";
            pstmt = dbcon.conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	qna = extractBoardFromResultSet(rs);
            }
            
			rs.close();
			pstmt.close();
			dbcon.conn.close();
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅
        } 
        return qna;
    }
    
    //게시글 결과물 저장
    private QnA extractBoardFromResultSet(ResultSet rs) throws SQLException {
        QnA qna = new QnA();
        qna.setNum(rs.getInt("num"));
        qna.setWriter(rs.getString("writer"));
        qna.setSubject(rs.getString("subject"));
        qna.setRegdate(rs.getString("reg_date"));
        qna.setRef(rs.getInt("ref"));
        qna.setRestep(rs.getInt("re_step"));
        qna.setRelevel(rs.getInt("re_level"));
        qna.setContent(rs.getString("content"));
        qna.setStatus(rs.getInt("status"));
        qna.setFile(rs.getString("file"));
        return qna;
    }
    

    // 게시글 개수 가져오기
    public int getBoardCount() {
    	dbcon.getCon();
    	
    	int count = 0;
    	
        try {
        	String sql = "select count(*) from qna";
            pstmt = dbcon.conn.prepareStatement(sql);	
        	rs = pstmt.executeQuery();
        	
            if (rs.next()) {
                count =  rs.getInt(1);
            }
            
            rs.close();
			pstmt.close();
			dbcon.conn.close();
			
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅
        }
        return count;
    }
    
    // 리스트 출력
    public List<QnA> getBoardListDescending(int startRow, int endRow, int parentId) {
    	dbcon.getCon();
    	List<QnA> boardList = new ArrayList<>();
        try {
        	String sql = "SELECT * FROM (SELECT *, CASE WHEN re_step = 0 THEN num ELSE ref END AS parentNum, ROW_NUMBER() OVER (ORDER BY parentNum DESC, re_step ASC) AS rnum FROM qna) AS temp WHERE (parentNum = ? OR (ref = ? AND parentNum = 0)) OR status = 1 AND rnum BETWEEN ? AND ?";
            pstmt = dbcon.conn.prepareStatement(sql);
        	pstmt.setInt(1, parentId);
            pstmt.setInt(2, parentId);
            pstmt.setInt(3, startRow);
            pstmt.setInt(4, endRow);
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	
                boardList.add(extractBoardFromResultSet(rs));
                
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅
        }
        return boardList;
    }

    // 게시글 작성
    public int insertBoard(QnA qna) {
        dbcon.getCon();
    	Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dbcon.conn;
            
            String sql = "INSERT INTO qna (writer, subject, reg_date, ref, re_step, re_level, content, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            // 부모 글의 번호를 가져옵니다.
            int parentId = qna.getRef();

            // 부모 글이 없으면(새 글인 경우)
            if (parentId == 0) {
                // ref 값을 0으로 설정합니다.
                pstmt.setInt(4, 0);
                // 답변 순서와 레벨은 0으로 설정합니다.
                pstmt.setInt(5, 0);
                pstmt.setInt(6, 0);
            } else {
                // 답글인 경우에는 부모 글의 ref 값을 사용합니다.
                pstmt.setInt(4, parentId);
                // 답글의 답변 순서와 레벨을 설정합니다.
                // 해당 부모 글의 마지막 답변 순서를 가져옵니다.
                int lastReStep = getLastReStep(conn, parentId);
                pstmt.setInt(5, lastReStep + 1);
                // 답글의 레벨은 부모 글의 레벨 + 1로 설정합니다.
                pstmt.setInt(6, qna.getRelevel() + 1);
            }

            // 나머지 필드 값들을 설정합니다.
            pstmt.setString(1, qna.getWriter());
            pstmt.setString(2, qna.getSubject());
            pstmt.setString(3, qna.getRegdate());
            pstmt.setString(7, qna.getContent());
            pstmt.setInt(8, 1);

            pstmt.executeUpdate();
            
            return 1; // 성공적으로 게시글이 삽입되었음을 나타내는 값
            
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅
            return 0; // 게시글 삽입 실패를 나타내는 값
        } finally {
            // 리소스 해제
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // 로깅
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // 로깅
                }
            }
        }
    }

    
    // 답글달기 메소드
    public int reply(int parentId, QnA qna) {
        dbcon.getCon();
        
        int result = 0; // 성공 여부를 나타내는 값

        try {
            dbcon.conn.setAutoCommit(false); // 트랜잭션 시작

            // 부모 글의 정보 가져오기
            QnA parentBoard = getBoard(parentId);

            // 해당 부모글의 답글 중에서 마지막 순서(re_step) 찾기
            int lastReStep = getLastReStep(dbcon.conn, parentId);

            // 새로운 답글의 순서와 깊이 설정
            qna.setRef(parentId);
            qna.setRestep(lastReStep + 1);
            qna.setRelevel(parentBoard.getRelevel() + 1);

            // 해당 게시물의 답글 순서(reStep)를 업데이트합니다.
            updateReStep(dbcon.conn, parentBoard.getRef(), qna.getRestep());

            // 답글을 작성합니다.
            String sql = "INSERT INTO qna (writer, subject, reg_date, ref, re_step, re_level, content, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = dbcon.conn.prepareStatement(sql);
            pstmt.setString(1, qna.getWriter());
            pstmt.setString(2, qna.getSubject());
            pstmt.setString(3, qna.getRegdate());
            pstmt.setInt(4, qna.getRef()); // ref 값 설정
            pstmt.setInt(5, qna.getRestep());
            pstmt.setInt(6, qna.getRelevel());
            pstmt.setString(7, qna.getContent());
            pstmt.setInt(8, qna.getStatus());

            // 쿼리를 실행하고 결과를 반환합니다.
            result = pstmt.executeUpdate();

            // 커밋
            dbcon.conn.commit();
        } catch (SQLException e) {
            // 롤백
            try {
                if (dbcon.conn != null) dbcon.conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace(); // 또는 로깅
        } 
        return result;
    }


    // 해당 게시물의 답글 순서(reStep)를 업데이트하는 메서드
    private void updateReStep(Connection conn, int ref, int reStep) throws SQLException {
        dbcon.getCon();
        
        try {
        	String sql = "UPDATE qna SET re_step = re_step + 1 WHERE ref = ? AND re_step > ?";
        	pstmt = dbcon.conn.prepareStatement(sql);
        	pstmt.setInt(1, ref);
            pstmt.setInt(2, reStep);
            pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    // 해당 부모글의 답글 중에서 마지막 순서(re_step) 찾기
    private int getLastReStep(Connection conn, int parentId) throws SQLException {
        dbcon.getCon();
        try {
        	String sql = "SELECT MAX(re_step) FROM qna WHERE ref = ?";
        	pstmt = dbcon.conn.prepareStatement(sql);
        	pstmt.setInt(1, parentId);
        	ResultSet rs = pstmt.executeQuery();
        	
            if (rs.next()) {
                return rs.getInt(1);
            } 	
		} catch (Exception e) {
			e.printStackTrace();
		}
        return 0;
    }

    // updateRef 메서드
    public void updateRef(int parentId, int ref) {
        dbcon.getCon();
        
        try {
        	String sql = "UPDATE qna SET ref = ? WHERE num = ?";
        	pstmt = dbcon.conn.prepareStatement(sql);
        	pstmt.setInt(1, ref);
            pstmt.setInt(2, parentId);
            pstmt.executeUpdate();            
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    
    //삭제 기능(답글이 존재하면 삭제 불가)
    public int delete(int num) {
    	dbcon.getCon();
        try {
            String sql = "SELECT COUNT(*) FROM qna WHERE ref = ?";
            pstmt = dbcon.conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        // 해당 게시글에 답글이 있는 경우 삭제 불가능
                        return 0;
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅
            return 0; // 예외 발생 시 삭제 실패로 처리
        }
        
        try { 
            String sql = "DELETE FROM qna WHERE num = ?";
            pstmt = dbcon.conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅
        }
        return 0;
    }
    
    // 업데이트
    public int update(int num, String subject, String content) {
    	dbcon.getCon();
        try{
        	String sql = "UPDATE qna SET subject = ?, content = ? WHERE num = ?";
        	pstmt = dbcon.conn.prepareStatement(sql);
            pstmt.setString(1, subject);
            pstmt.setString(2, content);
            pstmt.setInt(3, num);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅
        }
        return 0; // 업데이트 실패 시 0 반환
    }
	
}
