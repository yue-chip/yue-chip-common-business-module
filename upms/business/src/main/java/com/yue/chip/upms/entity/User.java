package com.yue.chip.upms.entity;

import com.yue.chip.upms.entity.definition.UserDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_user")
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Data
public class User extends UserDefinition {

}
