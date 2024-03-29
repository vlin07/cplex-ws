
package com.ncl.assisted_search.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cruisePackageRecommendation" type="{http://assisted-search.ncl.com/messages}cruisePackageRecommendation" maxOccurs="unbounded"/>
 *         &lt;element name="constraintViolation" type="{http://assisted-search.ncl.com/messages}constraintViolation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="thingsToDoRecommendation" type="{http://assisted-search.ncl.com/messages}thingsToDoRecommendation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cruisePackageRecommendation",
    "constraintViolation",
    "thingsToDoRecommendation"
})
@XmlRootElement(name = "GetAssistedSearchRecommendationResponse", namespace = "http://assisted-search.ncl.com/messages")
public class GetAssistedSearchRecommendationResponse {

    @XmlElement(required = true)
    protected List<CruisePackageRecommendation> cruisePackageRecommendation;
    protected List<ConstraintViolation> constraintViolation;
    protected List<ThingsToDoRecommendation> thingsToDoRecommendation;

    /**
     * Gets the value of the cruisePackageRecommendation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cruisePackageRecommendation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCruisePackageRecommendation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CruisePackageRecommendation }
     * 
     * 
     */
    public List<CruisePackageRecommendation> getCruisePackageRecommendation() {
        if (cruisePackageRecommendation == null) {
            cruisePackageRecommendation = new ArrayList<CruisePackageRecommendation>();
        }
        return this.cruisePackageRecommendation;
    }

    /**
     * Gets the value of the constraintViolation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constraintViolation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstraintViolation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConstraintViolation }
     * 
     * 
     */
    public List<ConstraintViolation> getConstraintViolation() {
        if (constraintViolation == null) {
            constraintViolation = new ArrayList<ConstraintViolation>();
        }
        return this.constraintViolation;
    }

    /**
     * Gets the value of the thingsToDoRecommendation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the thingsToDoRecommendation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getThingsToDoRecommendation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ThingsToDoRecommendation }
     * 
     * 
     */
    public List<ThingsToDoRecommendation> getThingsToDoRecommendation() {
        if (thingsToDoRecommendation == null) {
            thingsToDoRecommendation = new ArrayList<ThingsToDoRecommendation>();
        }
        return this.thingsToDoRecommendation;
    }

}
