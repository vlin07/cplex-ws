//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package com.ncl.assisted_search.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for discTypes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="discTypes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BESTFARE" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MILITARY" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AARP" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LATITUDES" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SENIOR" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="UNION" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="TEAMMEMBER" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SPECIALFARES" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FRIENDSFAMILY" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="stateCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "discTypes", namespace = "http://assisted-search.ncl.com/messages", propOrder = {
    "bestfare",
    "military",
    "aarp",
    "latitudes",
    "senior",
    "union",
    "teammember",
    "specialfares",
    "friendsfamily",
    "stateCode"
})
public class DiscTypes {

    @XmlElement(name = "BESTFARE", defaultValue = "true")
    protected boolean bestfare;
    @XmlElement(name = "MILITARY")
    protected boolean military;
    @XmlElement(name = "AARP")
    protected boolean aarp;
    @XmlElement(name = "LATITUDES")
    protected boolean latitudes;
    @XmlElement(name = "SENIOR")
    protected boolean senior;
    @XmlElement(name = "UNION")
    protected boolean union;
    @XmlElement(name = "TEAMMEMBER")
    protected boolean teammember;
    @XmlElement(name = "SPECIALFARES")
    protected boolean specialfares;
    @XmlElement(name = "FRIENDSFAMILY")
    protected boolean friendsfamily;
    @XmlElement(required = true)
    protected String stateCode;

    /**
     * Gets the value of the bestfare property.
     * 
     */
    public boolean isBESTFARE() {
        return bestfare;
    }

    /**
     * Sets the value of the bestfare property.
     * 
     */
    public void setBESTFARE(boolean value) {
        this.bestfare = value;
    }

    /**
     * Gets the value of the military property.
     * 
     */
    public boolean isMILITARY() {
        return military;
    }

    /**
     * Sets the value of the military property.
     * 
     */
    public void setMILITARY(boolean value) {
        this.military = value;
    }

    /**
     * Gets the value of the aarp property.
     * 
     */
    public boolean isAARP() {
        return aarp;
    }

    /**
     * Sets the value of the aarp property.
     * 
     */
    public void setAARP(boolean value) {
        this.aarp = value;
    }

    /**
     * Gets the value of the latitudes property.
     * 
     */
    public boolean isLATITUDES() {
        return latitudes;
    }

    /**
     * Sets the value of the latitudes property.
     * 
     */
    public void setLATITUDES(boolean value) {
        this.latitudes = value;
    }

    /**
     * Gets the value of the senior property.
     * 
     */
    public boolean isSENIOR() {
        return senior;
    }

    /**
     * Sets the value of the senior property.
     * 
     */
    public void setSENIOR(boolean value) {
        this.senior = value;
    }

    /**
     * Gets the value of the union property.
     * 
     */
    public boolean isUNION() {
        return union;
    }

    /**
     * Sets the value of the union property.
     * 
     */
    public void setUNION(boolean value) {
        this.union = value;
    }

    /**
     * Gets the value of the teammember property.
     * 
     */
    public boolean isTEAMMEMBER() {
        return teammember;
    }

    /**
     * Sets the value of the teammember property.
     * 
     */
    public void setTEAMMEMBER(boolean value) {
        this.teammember = value;
    }

    /**
     * Gets the value of the specialfares property.
     * 
     */
    public boolean isSPECIALFARES() {
        return specialfares;
    }

    /**
     * Sets the value of the specialfares property.
     * 
     */
    public void setSPECIALFARES(boolean value) {
        this.specialfares = value;
    }

    /**
     * Gets the value of the friendsfamily property.
     * 
     */
    public boolean isFRIENDSFAMILY() {
        return friendsfamily;
    }

    /**
     * Sets the value of the friendsfamily property.
     * 
     */
    public void setFRIENDSFAMILY(boolean value) {
        this.friendsfamily = value;
    }

    /**
     * Gets the value of the stateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Sets the value of the stateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateCode(String value) {
        this.stateCode = value;
    }

}
