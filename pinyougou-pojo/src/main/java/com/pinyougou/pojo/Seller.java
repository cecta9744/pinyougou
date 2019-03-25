package com.pinyougou.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
 
/**
 * 商家实体
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年12月14日 下午2:02:20
 * @version 1.0
 */
@Table(name="tb_seller")
public class Seller implements Serializable{
   
	private static final long serialVersionUID = -6636378626574886353L;
	/** 登录名 */
	@Id @Column(name="seller_id")
	private String sellerId;
	/** 公司名称 */
	@Column(name="name")
    private String name;
    /** 店铺名称 */
	@Column(name="nick_name")
    private String nickName;
    /** 登录密码 */
	@Column(name="password")
    private String password;
    /** 公司邮箱 */
	@Column(name="email")
    private String email;
    /** 手机 */
	@Column(name="mobile")
    private String mobile;
    /** 公司电话 */
	@Column(name="telephone")
    private String telephone;
    /** 状态 */
	@Column(name="status")
    private String status;
    /** 公司详细地址 */
	@Column(name="address_detail")
    private String addressDetail;
    /** 联系人姓名 */
	@Column(name="linkman_name")
    private String linkmanName;
    /** 联系人QQ */
	@Column(name="linkman_qq")
    private String linkmanQq;
    /** 联系人手机 */
	@Column(name="linkman_mobile")
    private String linkmanMobile;
    /** 联系人EMAIL */
	@Column(name="linkman_email")
    private String linkmanEmail;
    /** 营业执照号 */
	@Column(name="license_number")
    private String licenseNumber;
    /** 税务登记证号 */
	@Column(name="tax_number")
    private String taxNumber;
    /** 组织机构代码证 */
	@Column(name="org_number")
    private String orgNumber;
    /** 邮编 */
	@Column(name="address")
    private Long address;
    /** 公司LOGO */
	@Column(name="logo_pic")
    private String logoPic;
    /** 创建时间 */
	@Column(name="create_time")
    private Date createTime;
    /** 法定代表人 */
	@Column(name="legal_person")
    private String legalPerson;
    /** 法定代表人身份证号 */
	@Column(name="legal_person_card_id")
    private String legalPersonCardId;
    /** 开户行名称 */
	@Column(name="bank_name")
    private String bankName;
    /** 开户行支行 */
	@Column(name="bank_user")
    private String bankUser;
    /** 银行账号 */
	@Column(name="brief")
    private String brief;
    
    /** setter and getter method */
    public String getSellerId() {
        return sellerId;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    public String getAddressDetail() {
        return addressDetail;
    }
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }
    public String getLinkmanName() {
        return linkmanName;
    }
    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName == null ? null : linkmanName.trim();
    }
    public String getLinkmanQq() {
        return linkmanQq;
    }
    public void setLinkmanQq(String linkmanQq) {
        this.linkmanQq = linkmanQq == null ? null : linkmanQq.trim();
    }
    public String getLinkmanMobile() {
        return linkmanMobile;
    }
    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile == null ? null : linkmanMobile.trim();
    }
    public String getLinkmanEmail() {
        return linkmanEmail;
    }
    public void setLinkmanEmail(String linkmanEmail) {
        this.linkmanEmail = linkmanEmail == null ? null : linkmanEmail.trim();
    }
    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
    }
    public String getTaxNumber() {
        return taxNumber;
    }
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber == null ? null : taxNumber.trim();
    }
    public String getOrgNumber() {
        return orgNumber;
    }
    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber == null ? null : orgNumber.trim();
    }
    public Long getAddress() {
        return address;
    }
    public void setAddress(Long address) {
        this.address = address;
    }
    public String getLogoPic() {
        return logoPic;
    }
    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic == null ? null : logoPic.trim();
    }
    public String getBrief() {
        return brief;
    }
    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getLegalPerson() {
        return legalPerson;
    }
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }
    public String getLegalPersonCardId() {
        return legalPersonCardId;
    }
    public void setLegalPersonCardId(String legalPersonCardId) {
        this.legalPersonCardId = legalPersonCardId == null ? null : legalPersonCardId.trim();
    }
    public String getBankUser() {
        return bankUser;
    }
    public void setBankUser(String bankUser) {
        this.bankUser = bankUser == null ? null : bankUser.trim();
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }
}