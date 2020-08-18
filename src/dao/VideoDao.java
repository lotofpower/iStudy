package dao;

import entity.Video;


import java.sql.SQLException;
import java.util.List;

public interface VideoDao {
    //获取显示列表
    public List getshowlist(Page page) throws Exception;


    //获取总记录数
    public int getCount();

    //添加商品
    public int insertVideo(List<Object> objects );

    //根据ID删除
    public int deleteVideo(int id);

    //根据id查询
    public Video querybyId(int id);

    //修改商品
    public int updataVideo(List<Object> objects );
}
