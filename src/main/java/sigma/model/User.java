package sigma.model;

public class User {
	Long id;
	String uuid;
	String username;
	String phone;
	String password;
	Long dateJoined;

	String stripeId;
	Long okayPlanId;
	String stripeSubscriptionId;

	String stripeToken;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Long dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

	public Long getOkayPlanId() {
		return okayPlanId;
	}

	public void setOkayPlanId(Long okayPlanId) {
		this.okayPlanId = okayPlanId;
	}

	public String getStripeSubscriptionId() {
		return stripeSubscriptionId;
	}

	public void setStripeSubscriptionId(String stripeSubscriptionId) {
		this.stripeSubscriptionId = stripeSubscriptionId;
	}

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}
}

