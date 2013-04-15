
package com.ncl.assisted_search.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for constraintViolation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="constraintViolation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="packageLimitViolation" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="thingsToDoLimitViolation" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="destinationViolation" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="roomTypeLimitViolation" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="itineraryUniquenessViolation" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "constraintViolation", namespace = "http://assisted-search.ncl.com/messages", propOrder = {
    "packageLimitViolation",
    "thingsToDoLimitViolation",
    "destinationViolation",
    "roomTypeLimitViolation",
    "itineraryUniquenessViolation"
})
public class ConstraintViolation {

    protected double packageLimitViolation;
    protected double thingsToDoLimitViolation;
    protected double destinationViolation;
    protected double roomTypeLimitViolation;
    protected double itineraryUniquenessViolation;

    /**
     * Gets the value of the packageLimitViolation property.
     * 
     */
    public double getPackageLimitViolation() {
        return packageLimitViolation;
    }

    /**
     * Sets the value of the packageLimitViolation property.
     * 
     */
    public void setPackageLimitViolation(double value) {
        this.packageLimitViolation = value;
    }

    /**
     * Gets the value of the thingsToDoLimitViolation property.
     * 
     */
    public double getThingsToDoLimitViolation() {
        return thingsToDoLimitViolation;
    }

    /**
     * Sets the value of the thingsToDoLimitViolation property.
     * 
     */
    public void setThingsToDoLimitViolation(double value) {
        this.thingsToDoLimitViolation = value;
    }

    /**
     * Gets the value of the destinationViolation property.
     * 
     */
    public double getDestinationViolation() {
        return destinationViolation;
    }

    /**
     * Sets the value of the destinationViolation property.
     * 
     */
    public void setDestinationViolation(double value) {
        this.destinationViolation = value;
    }

    /**
     * Gets the value of the roomTypeLimitViolation property.
     * 
     */
    public double getRoomTypeLimitViolation() {
        return roomTypeLimitViolation;
    }

    /**
     * Sets the value of the roomTypeLimitViolation property.
     * 
     */
    public void setRoomTypeLimitViolation(double value) {
        this.roomTypeLimitViolation = value;
    }

    /**
     * Gets the value of the itineraryUniquenessViolation property.
     * 
     */
    public double getItineraryUniquenessViolation() {
        return itineraryUniquenessViolation;
    }

    /**
     * Sets the value of the itineraryUniquenessViolation property.
     * 
     */
    public void setItineraryUniquenessViolation(double value) {
        this.itineraryUniquenessViolation = value;
    }

}
