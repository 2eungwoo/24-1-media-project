package mediaproject.its.domain.dto;

import mediaproject.its.domain.entity.User;

public interface PostInterface {
    Integer getId();
    String getTitle();
    String getUsername();
    Integer getView_count();
    Integer getLikes_count();
    Integer getComments_count();
    String getHiring_type();
    String getPosition_type();
    String getProcess_type();
    String getRecruiting_type();
    String getTechstack_type();
}