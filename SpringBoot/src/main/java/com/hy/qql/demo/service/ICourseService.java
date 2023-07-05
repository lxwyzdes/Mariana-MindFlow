package com.hy.qql.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qql
 * @since 2022-04-01
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> page, String name);

    void setStudentCourse(Integer courseId, Integer studentId);
}
