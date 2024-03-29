
package com.ncl.assisted_search.messages;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="destinationSet" type="{http://assisted-search.ncl.com/messages}destinationSet"/>
 *         &lt;element name="accomodationSet" type="{http://assisted-search.ncl.com/messages}accomodationSet"/>
 *         &lt;element name="thingToDoSet" type="{http://assisted-search.ncl.com/messages}thingToDoSet"/>
 *         &lt;element name="interestSet" type="{http://assisted-search.ncl.com/messages}interestSet"/>
 *         &lt;element name="departurePortSet" type="{http://assisted-search.ncl.com/messages}departurePortSet"/>
 *         &lt;element name="durationSet" type="{http://assisted-search.ncl.com/messages}durationSet"/>
 *         &lt;element name="flexDatesSet" type="{http://assisted-search.ncl.com/messages}flexDatesSet" minOccurs="0"/>
 *         &lt;element name="promoCodeSet" type="{http://assisted-search.ncl.com/messages}promoCodeSet"/>
 *         &lt;element name="priceRangeSet" type="{http://assisted-search.ncl.com/messages}priceRangeSet"/>
 *         &lt;element name="stateroomView" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stateroomSpace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stateroomLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numberOfGuests" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="userWeightSet" type="{http://assisted-search.ncl.com/messages}userWeightSet"/>
 *         &lt;element name="recommReqLimitSet" type="{http://assisted-search.ncl.com/messages}recommReqLimitSet"/>
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
    "destinationSet",
    "accomodationSet",
    "thingToDoSet",
    "interestSet",
    "departurePortSet",
    "durationSet",
    "flexDatesSet",
    "promoCodeSet",
    "priceRangeSet",
    "stateroomView",
    "stateroomSpace",
    "stateroomLocation",
    "numberOfGuests",
    "userWeightSet",
    "recommReqLimitSet"
})
@XmlRootElement(name = "GetAssistedSearchRecommendation", namespace = "http://assisted-search.ncl.com/messages")
public class GetAssistedSearchRecommendation {

    @XmlElement(required = true)
    protected DestinationSet destinationSet;
    @XmlElement(required = true)
    protected AccomodationSet accomodationSet;
    @XmlElement(required = true)
    protected ThingToDoSet thingToDoSet;
    @XmlElement(required = true)
    protected InterestSet interestSet;
    @XmlElement(required = true)
    protected DeparturePortSet departurePortSet;
    @XmlElement(required = true)
    protected DurationSet durationSet;
    @XmlElementRef(name = "flexDatesSet", type = JAXBElement.class)
    protected JAXBElement<FlexDatesSet> flexDatesSet;
    @XmlElement(required = true)
    protected PromoCodeSet promoCodeSet;
    @XmlElement(required = true)
    protected PriceRangeSet priceRangeSet;
    @XmlElement(required = true)
    protected String stateroomView;
    @XmlElement(required = true)
    protected String stateroomSpace;
    @XmlElement(required = true)
    protected String stateroomLocation;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger numberOfGuests;
    @XmlElement(required = true)
    protected UserWeightSet userWeightSet;
    @XmlElement(required = true)
    protected RecommReqLimitSet recommReqLimitSet;

    /**
     * Gets the value of the destinationSet property.
     * 
     * @return
     *     possible object is
     *     {@link DestinationSet }
     *     
     */
    public DestinationSet getDestinationSet() {
        return destinationSet;
    }

    /**
     * Sets the value of the destinationSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link DestinationSet }
     *     
     */
    public void setDestinationSet(DestinationSet value) {
        this.destinationSet = value;
    }

    /**
     * Gets the value of the accomodationSet property.
     * 
     * @return
     *     possible object is
     *     {@link AccomodationSet }
     *     
     */
    public AccomodationSet getAccomodationSet() {
        return accomodationSet;
    }

    /**
     * Sets the value of the accomodationSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccomodationSet }
     *     
     */
    public void setAccomodationSet(AccomodationSet value) {
        this.accomodationSet = value;
    }

    /**
     * Gets the value of the thingToDoSet property.
     * 
     * @return
     *     possible object is
     *     {@link ThingToDoSet }
     *     
     */
    public ThingToDoSet getThingToDoSet() {
        return thingToDoSet;
    }

    /**
     * Sets the value of the thingToDoSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThingToDoSet }
     *     
     */
    public void setThingToDoSet(ThingToDoSet value) {
        this.thingToDoSet = value;
    }

    /**
     * Gets the value of the interestSet property.
     * 
     * @return
     *     possible object is
     *     {@link InterestSet }
     *     
     */
    public InterestSet getInterestSet() {
        return interestSet;
    }

    /**
     * Sets the value of the interestSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterestSet }
     *     
     */
    public void setInterestSet(InterestSet value) {
        this.interestSet = value;
    }

    /**
     * Gets the value of the departurePortSet property.
     * 
     * @return
     *     possible object is
     *     {@link DeparturePortSet }
     *     
     */
    public DeparturePortSet getDeparturePortSet() {
        return departurePortSet;
    }

    /**
     * Sets the value of the departurePortSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeparturePortSet }
     *     
     */
    public void setDeparturePortSet(DeparturePortSet value) {
        this.departurePortSet = value;
    }

    /**
     * Gets the value of the durationSet property.
     * 
     * @return
     *     possible object is
     *     {@link DurationSet }
     *     
     */
    public DurationSet getDurationSet() {
        return durationSet;
    }

    /**
     * Sets the value of the durationSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link DurationSet }
     *     
     */
    public void setDurationSet(DurationSet value) {
        this.durationSet = value;
    }

    /**
     * Gets the value of the flexDatesSet property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link FlexDatesSet }{@code >}
     *     
     */
    public JAXBElement<FlexDatesSet> getFlexDatesSet() {
        return flexDatesSet;
    }

    /**
     * Sets the value of the flexDatesSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link FlexDatesSet }{@code >}
     *     
     */
    public void setFlexDatesSet(JAXBElement<FlexDatesSet> value) {
        this.flexDatesSet = ((JAXBElement<FlexDatesSet> ) value);
    }

    /**
     * Gets the value of the promoCodeSet property.
     * 
     * @return
     *     possible object is
     *     {@link PromoCodeSet }
     *     
     */
    public PromoCodeSet getPromoCodeSet() {
        return promoCodeSet;
    }

    /**
     * Sets the value of the promoCodeSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromoCodeSet }
     *     
     */
    public void setPromoCodeSet(PromoCodeSet value) {
        this.promoCodeSet = value;
    }

    /**
     * Gets the value of the priceRangeSet property.
     * 
     * @return
     *     possible object is
     *     {@link PriceRangeSet }
     *     
     */
    public PriceRangeSet getPriceRangeSet() {
        return priceRangeSet;
    }

    /**
     * Sets the value of the priceRangeSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceRangeSet }
     *     
     */
    public void setPriceRangeSet(PriceRangeSet value) {
        this.priceRangeSet = value;
    }

    /**
     * Gets the value of the stateroomView property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateroomView() {
        return stateroomView;
    }

    /**
     * Sets the value of the stateroomView property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateroomView(String value) {
        this.stateroomView = value;
    }

    /**
     * Gets the value of the stateroomSpace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateroomSpace() {
        return stateroomSpace;
    }

    /**
     * Sets the value of the stateroomSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateroomSpace(String value) {
        this.stateroomSpace = value;
    }

    /**
     * Gets the value of the stateroomLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateroomLocation() {
        return stateroomLocation;
    }

    /**
     * Sets the value of the stateroomLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateroomLocation(String value) {
        this.stateroomLocation = value;
    }

    /**
     * Gets the value of the numberOfGuests property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfGuests() {
        return numberOfGuests;
    }

    /**
     * Sets the value of the numberOfGuests property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfGuests(BigInteger value) {
        this.numberOfGuests = value;
    }

    /**
     * Gets the value of the userWeightSet property.
     * 
     * @return
     *     possible object is
     *     {@link UserWeightSet }
     *     
     */
    public UserWeightSet getUserWeightSet() {
        return userWeightSet;
    }

    /**
     * Sets the value of the userWeightSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserWeightSet }
     *     
     */
    public void setUserWeightSet(UserWeightSet value) {
        this.userWeightSet = value;
    }

    /**
     * Gets the value of the recommReqLimitSet property.
     * 
     * @return
     *     possible object is
     *     {@link RecommReqLimitSet }
     *     
     */
    public RecommReqLimitSet getRecommReqLimitSet() {
        return recommReqLimitSet;
    }

    /**
     * Sets the value of the recommReqLimitSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecommReqLimitSet }
     *     
     */
    public void setRecommReqLimitSet(RecommReqLimitSet value) {
        this.recommReqLimitSet = value;
    }

}
