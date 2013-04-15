
package com.ncl.assisted_search.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cruisePackageRecommendation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cruisePackageRecommendation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sailID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="packageID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itineraryID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="roomType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="meta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interestPrefContrib" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="accomodationPrefContrib" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="roomViolationValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="thingsToDoViolationValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="priceViolationValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="dateViolationValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="durationViolationValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="destinationViolationValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="portViolationValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="drupalWeightContrib" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="isRecommended" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pricePerGuest" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cruisePackageRecommendation", namespace = "http://assisted-search.ncl.com/messages", propOrder = {
    "sailID",
    "packageID",
    "dateID",
    "itineraryID",
    "destination",
    "roomType",
    "meta",
    "interestPrefContrib",
    "accomodationPrefContrib",
    "roomViolationValue",
    "thingsToDoViolationValue",
    "priceViolationValue",
    "dateViolationValue",
    "durationViolationValue",
    "destinationViolationValue",
    "portViolationValue",
    "drupalWeightContrib",
    "isRecommended",
    "pricePerGuest"
})
public class CruisePackageRecommendation {

    @XmlElement(required = true)
    protected String sailID;
    @XmlElement(required = true)
    protected String packageID;
    @XmlElement(required = true)
    protected String dateID;
    @XmlElement(required = true)
    protected String itineraryID;
    @XmlElement(required = true)
    protected String destination;
    @XmlElement(required = true)
    protected String roomType;
    @XmlElement(required = true)
    protected String meta;
    protected double interestPrefContrib;
    protected double accomodationPrefContrib;
    protected double roomViolationValue;
    protected double thingsToDoViolationValue;
    protected double priceViolationValue;
    protected double dateViolationValue;
    protected double durationViolationValue;
    protected double destinationViolationValue;
    protected double portViolationValue;
    protected double drupalWeightContrib;
    protected int isRecommended;
    protected double pricePerGuest;

    /**
     * Gets the value of the sailID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSailID() {
        return sailID;
    }

    /**
     * Sets the value of the sailID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSailID(String value) {
        this.sailID = value;
    }

    /**
     * Gets the value of the packageID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageID() {
        return packageID;
    }

    /**
     * Sets the value of the packageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageID(String value) {
        this.packageID = value;
    }

    /**
     * Gets the value of the dateID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateID() {
        return dateID;
    }

    /**
     * Sets the value of the dateID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateID(String value) {
        this.dateID = value;
    }

    /**
     * Gets the value of the itineraryID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItineraryID() {
        return itineraryID;
    }

    /**
     * Sets the value of the itineraryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItineraryID(String value) {
        this.itineraryID = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the roomType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Sets the value of the roomType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomType(String value) {
        this.roomType = value;
    }

    /**
     * Gets the value of the meta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeta() {
        return meta;
    }

    /**
     * Sets the value of the meta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeta(String value) {
        this.meta = value;
    }

    /**
     * Gets the value of the interestPrefContrib property.
     * 
     */
    public double getInterestPrefContrib() {
        return interestPrefContrib;
    }

    /**
     * Sets the value of the interestPrefContrib property.
     * 
     */
    public void setInterestPrefContrib(double value) {
        this.interestPrefContrib = value;
    }

    /**
     * Gets the value of the accomodationPrefContrib property.
     * 
     */
    public double getAccomodationPrefContrib() {
        return accomodationPrefContrib;
    }

    /**
     * Sets the value of the accomodationPrefContrib property.
     * 
     */
    public void setAccomodationPrefContrib(double value) {
        this.accomodationPrefContrib = value;
    }

    /**
     * Gets the value of the roomViolationValue property.
     * 
     */
    public double getRoomViolationValue() {
        return roomViolationValue;
    }

    /**
     * Sets the value of the roomViolationValue property.
     * 
     */
    public void setRoomViolationValue(double value) {
        this.roomViolationValue = value;
    }

    /**
     * Gets the value of the thingsToDoViolationValue property.
     * 
     */
    public double getThingsToDoViolationValue() {
        return thingsToDoViolationValue;
    }

    /**
     * Sets the value of the thingsToDoViolationValue property.
     * 
     */
    public void setThingsToDoViolationValue(double value) {
        this.thingsToDoViolationValue = value;
    }

    /**
     * Gets the value of the priceViolationValue property.
     * 
     */
    public double getPriceViolationValue() {
        return priceViolationValue;
    }

    /**
     * Sets the value of the priceViolationValue property.
     * 
     */
    public void setPriceViolationValue(double value) {
        this.priceViolationValue = value;
    }

    /**
     * Gets the value of the dateViolationValue property.
     * 
     */
    public double getDateViolationValue() {
        return dateViolationValue;
    }

    /**
     * Sets the value of the dateViolationValue property.
     * 
     */
    public void setDateViolationValue(double value) {
        this.dateViolationValue = value;
    }

    /**
     * Gets the value of the durationViolationValue property.
     * 
     */
    public double getDurationViolationValue() {
        return durationViolationValue;
    }

    /**
     * Sets the value of the durationViolationValue property.
     * 
     */
    public void setDurationViolationValue(double value) {
        this.durationViolationValue = value;
    }

    /**
     * Gets the value of the destinationViolationValue property.
     * 
     */
    public double getDestinationViolationValue() {
        return destinationViolationValue;
    }

    /**
     * Sets the value of the destinationViolationValue property.
     * 
     */
    public void setDestinationViolationValue(double value) {
        this.destinationViolationValue = value;
    }

    /**
     * Gets the value of the portViolationValue property.
     * 
     */
    public double getPortViolationValue() {
        return portViolationValue;
    }

    /**
     * Sets the value of the portViolationValue property.
     * 
     */
    public void setPortViolationValue(double value) {
        this.portViolationValue = value;
    }

    /**
     * Gets the value of the drupalWeightContrib property.
     * 
     */
    public double getDrupalWeightContrib() {
        return drupalWeightContrib;
    }

    /**
     * Sets the value of the drupalWeightContrib property.
     * 
     */
    public void setDrupalWeightContrib(double value) {
        this.drupalWeightContrib = value;
    }

    /**
     * Gets the value of the isRecommended property.
     * 
     */
    public int getIsRecommended() {
        return isRecommended;
    }

    /**
     * Sets the value of the isRecommended property.
     * 
     */
    public void setIsRecommended(int value) {
        this.isRecommended = value;
    }

    /**
     * Gets the value of the pricePerGuest property.
     * 
     */
    public double getPricePerGuest() {
        return pricePerGuest;
    }

    /**
     * Sets the value of the pricePerGuest property.
     * 
     */
    public void setPricePerGuest(double value) {
        this.pricePerGuest = value;
    }

}
