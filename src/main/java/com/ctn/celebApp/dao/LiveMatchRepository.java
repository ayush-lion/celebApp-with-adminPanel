package com.ctn.celebApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctn.celebApp.entity.LiveMatch;

public interface LiveMatchRepository extends JpaRepository<LiveMatch, String>{

}
