package com.mfz.function.testfunction.mapper;

import java.util.List;
import java.util.Map;


public interface TestMapper {
	public List getUserList(Map parMap);
	public int updateRecord(Map parMap);
}