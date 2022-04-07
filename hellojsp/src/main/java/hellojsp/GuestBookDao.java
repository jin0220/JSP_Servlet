package hellojsp;

import java.util.List;

public interface GuestBookDao {
	
	public List<GuestBookVo> getList();

	public int insert(GuestBookVo vo);

	public int delete(GuestBookVo vo);
}
