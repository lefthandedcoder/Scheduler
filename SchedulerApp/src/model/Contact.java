package model;

/**
 *
 * @author Christian Dye
 */
public class Contact {

    /**
     * The contact ID
     */
    private int contactId;

    /**
     * The contact name
     */
    private String contactName;

    /**
     * The contact email
     */
    private String contactEmail;

    /**
     *
     * @param contactId
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets the contact ID
     * @return
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact ID
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets the contact name
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the contact email
     * @return
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the contact email
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

}
