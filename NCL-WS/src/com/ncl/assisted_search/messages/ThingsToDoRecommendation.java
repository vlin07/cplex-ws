
package com.ncl.assisted_search.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for thingsToDoRecommendation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="thingsToDoRecommendation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="thingsToDoID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interestID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interestPrefContrib" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="drupalWeightContrib" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="isRecommended" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "thingsToDoRecommendation", namespace = "http://assisted-search.ncl.com/messages", propOrder = {
    "thingsToDoID",
    "interestID",
    "interestPrefContrib",
    "drupalWeightContrib",
    "isRecommended"
})
public class ThingsToDoRecommendation {

    @XmlElement(required = true)
    protected String thingsToDoID;
    @XmlElement(required = true)
    protected String interestID;
    protected double interestPrefContrib;
    protected double drupalWeightContrib;
    protected int isRecommended;

    /**
     * Gets the value of the thingsToDoID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThingsToDoID() {
        return thingsToDoID;
    }

    /**
     * Sets the value of the thingsToDoID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThingsToDoID(String value) {
        this.thingsToDoID = value;
    }

    /**
     * Gets the value of the interestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterestID() {
        return interestID;
    }

    /**
     * Sets the value of the interestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterestID(String value) {
        this.interestID = value;
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

}
