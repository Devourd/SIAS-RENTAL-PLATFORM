package com.ldu.pojo;
/**
 * Commodity development joint query
 * @author lyq
 *
 */
import java.util.List;

public class CommentExtend extends Goods{
    private List<Comments> comments;
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	
	
}