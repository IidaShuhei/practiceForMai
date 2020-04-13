package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Member;
import com.example.domain.TmpMember;

@Repository
public class PracticeRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<TmpMember> TmpRowMapper = new BeanPropertyRowMapper<TmpMember>(TmpMember.class);
	private static final RowMapper<Member> rowMapper = new BeanPropertyRowMapper<Member>(Member.class);
	
	public void insert(TmpMember tmpMember) {
		String sql = "insert into practice_tmp_users(name,mail,password,uuid)values(:name,:mail,:password,:uuid)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(tmpMember);
		template.update(sql, param);
	}
	
	public TmpMember load(String uuid) {
		String sql = "select id,name,mail,password,uuid from practice_tmp_users where uuid =:uuid";
		SqlParameterSource param = new MapSqlParameterSource().addValue("uuid", uuid);
		List<TmpMember> tmp = template.query(sql, param, TmpRowMapper);
		if(tmp.size() == 0) {
			return null;
		} else {
			return tmp.get(0);
		}
	}
	
	public Member findByMail(String mail) {
		String sql = "select name,mail,password from practice_users where mail =:mail";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mail", mail);
		List<Member> member = template.query(sql, param, rowMapper);
		if(member.size() == 0) {
			return null;
		} else {
			return member.get(0);
		}
	}
	
	public void register(Member member) {
		String sql = "insert into practice_users(name,mail,password)values(:name,:mail,:password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(member);
		template.update(sql, param);
	}
	
	public void delete(String uuid) {
		String sql = "delete from practice_tmp_users where uuid =:uuid";
		SqlParameterSource param = new MapSqlParameterSource().addValue("uuid", uuid);
		template.update(sql, param);
	}
}
