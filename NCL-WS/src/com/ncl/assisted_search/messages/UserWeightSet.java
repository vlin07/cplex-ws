
package com.ncl.assisted_search.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userWeightSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userWeightSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wc1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="wc2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="wc3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="wc4" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="wc5" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userWeightSet", namespace = "http://assisted-search.ncl.com/messages", propOrder = {
    "wc1",
    "wc2",
    "wc3",
    "wc4",
    "wc5"
})
public class UserWeightSet {

    protected double wc1;
    protected double wc2;
    protected double wc3;
    protected double wc4;
    protected double wc5;

    /**
     * Gets the value of the wc1 property.
     * 
     */
    public double getWc1() {
        return wc1;
    }

    /**
     * Sets the value of the wc1 property.
     * 
     */
    public void setWc1(double value) {
        this.wc1 = value;
    }

    /**
     * Gets the value of the wc2 property.
     * 
     */
    public double getWc2() {
        return wc2;
    }

    /**
     * Sets the value of the wc2 property.
     * 
     */
    public void setWc2(double value) {
        this.wc2 = value;
    }

    /**
     * Gets the value of the wc3 property.
     * 
     */
    public double getWc3() {
        return wc3;
    }

    /**
     * Sets the value of the wc3 property.
     * 
     */
    public void setWc3(double value) {
        this.wc3 = value;
    }

    /**
     * Gets the value of the wc4 property.
     * 
     */
    public double getWc4() {
        return wc4;
    }

    /**
     * Sets the value of the wc4 property.
     * 
     */
    public void setWc4(double value) {
        this.wc4 = value;
    }

    /**
     * Gets the value of the wc5 property.
     * 
     */
    public double getWc5() {
        return wc5;
    }

    /**
     * Sets the value of the wc5 property.
     * 
     */
    public void setWc5(double value) {
        this.wc5 = value;
    }

}
