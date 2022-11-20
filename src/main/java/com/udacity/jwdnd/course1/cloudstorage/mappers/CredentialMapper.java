package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from credentials where userid = #{userid}")
    List<Credential> findAllByUserid(int userid);

    @Insert("insert into credentials(url, username, key, password, userid) values (#{url}, #{username}, #{key}, #{password}, #{userid})")
    int save(Credential credential);

    @Delete("delete from credentials where credentialid = #{id}")
    void delete(int id);

    @Select("select * from credentials where credentialid = #{id}")
    Credential findById(int id);

    @Update("update credentials set url = #{url}, username=#{username}, password=#{password}, key=#{key} where credentialid=#{credentialid}")
    void merge(Credential credential);
}
