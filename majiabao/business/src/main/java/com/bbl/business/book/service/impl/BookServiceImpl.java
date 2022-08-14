package com.bbl.business.book.service.impl;

import com.bbl.business.book.entity.Book;
import com.bbl.business.book.mapper.BookMapper;
import com.bbl.business.book.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2019-11-19
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

}
