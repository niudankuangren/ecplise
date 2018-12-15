package com.tensquare.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;

import entity.PageResult;
import util.IdWorker;

@Service

public class LabelService {

	@Autowired
	private LabelDao labelDao;

	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部标签
	 * 
	 * @return
	 */
	public List<Label> findAll() {

		return labelDao.findAll();
	}

	/**
	 * 根据ID查询标签
	 * 
	 * @return
	 */
	public Label findById(String id) {

		return labelDao.findById(id).get();
	}

	/**
	 * 增加标签
	 * 
	 * @param label
	 */

	public void add(Label label) {

		label.setId(idWorker.nextId() + "");// 设置ID
		labelDao.save(label);
	}

	/**
	 * 修改标签
	 * 
	 * @param label
	 */
	public void update(Label label) {

		labelDao.save(label);

	}

	/**
	 * 删除标签
	 * 
	 * @param id
	 */
	public void deleteById(String id) {

		labelDao.deleteById(id);

	}

	public List<Label> search(Label label) {

		return labelDao.findAll(new Specification<Label>() {

			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate pred = getPredicate(label, root, cb);
				return pred;
			}

		});

	}

	public PageResult<Label> search(Label label, int page, int size) {
		long total = 0;
		List rows = null;

		Pageable pageable=PageRequest.of(page-1, size);
		
		Page<Label> pageLabel=labelDao.findAll(new Specification<Label>() {

			@Override
			public Predicate toPredicate(Root<Label> arg0, CriteriaQuery<?> arg1, CriteriaBuilder arg2) {

				Predicate pred = getPredicate(label, arg0, arg2);
				return pred;
			}
		}, pageable);
		
		total=pageLabel.getTotalElements();
		rows=pageLabel.getContent();

		PageResult<Label> pageResult = new PageResult(total, rows);

		return pageResult;

	}

	private Predicate getPredicate(Label label, Root<Label> root, CriteriaBuilder cb) {
		List<Predicate> predicateList = new ArrayList<>();

		if (label.getLabelname() != null && !"".equals(label.getLabelname())) {

			Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
			predicateList.add(predicate);
		}
		if (label.getState() != null && !"".equals(label.getState())) {

			Predicate predicate = cb.like(root.get("state").as(String.class), "%" + label.getState() + "%");
			predicateList.add(predicate);
		}

		if (label.getState() != null && !"".equals(label.getState())) {

			Predicate predicate = cb.equal(root.get("state").as(String.class), "%" + label.getState() + "%");
			predicateList.add(predicate);
		}

		if (label.getCount() != null) {

			Predicate predicate = cb.equal(root.get("count").as(Long.class), "%" + label.getCount() + "%");
			predicateList.add(predicate);
		}

		if (label.getRecommend() != null && !"".equals(label.getRecommend())) {

			Predicate predicate = cb.like(root.get("recommend").as(String.class), "%" + label.getRecommend() + "%");
			predicateList.add(predicate);
		}

		Predicate[] predicate = new Predicate[predicateList.size()];
		predicateList.toArray(predicate);
		Predicate pred = cb.and(predicate);
		return pred;
	}

}
