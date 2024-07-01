package com.alkl1m.contractor.domain.entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author alkl1m
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "contractor", name = "contractor")
public class Contractor {

    @Id
    private String id;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "name")
    private String name;
    @Column(name = "name_full")
    private String nameFull;
    @Column(name = "inn")
    private String inn;
    @Column(name = "ogrn")
    private String ogrn;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "industry")
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "org_form")
    private OrgForm orgForm;

    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "modify_date")
    private Date modifyDate;
    @Column(name = "create_user_id")
    private String createUserId;
    @Column(name = "modify_user_id")
    private String modifyUserId;
    @Column(name = "is_active")
    private boolean isActive = true;

}
