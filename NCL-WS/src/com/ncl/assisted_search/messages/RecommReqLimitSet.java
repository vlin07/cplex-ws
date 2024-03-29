
package com.ncl.assisted_search.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recommReqLimitSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recommReqLimitSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numPackageRecomm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numThingsToDo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numDest" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numStateRoomTypes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recommReqLimitSet", namespace = "http://assisted-search.ncl.com/messages", propOrder = {
    "numPackageRecomm",
    "numThingsToDo",
    "numDest",
    "numStateRoomTypes"
})
public class RecommReqLimitSet {

    protected int numPackageRecomm;
    protected int numThingsToDo;
    protected int numDest;
    protected int numStateRoomTypes;

    /**
     * Gets the value of the numPackageRecomm property.
     * 
     */
    public int getNumPackageRecomm() {
        return numPackageRecomm;
    }

    /**
     * Sets the value of the numPackageRecomm property.
     * 
     */
    public void setNumPackageRecomm(int value) {
        this.numPackageRecomm = value;
    }

    /**
     * Gets the value of the numThingsToDo property.
     * 
     */
    public int getNumThingsToDo() {
        return numThingsToDo;
    }

    /**
     * Sets the value of the numThingsToDo property.
     * 
     */
    public void setNumThingsToDo(int value) {
        this.numThingsToDo = value;
    }

    /**
     * Gets the value of the numDest property.
     * 
     */
    public int getNumDest() {
        return numDest;
    }

    /**
     * Sets the value of the numDest property.
     * 
     */
    public void setNumDest(int value) {
        this.numDest = value;
    }

    /**
     * Gets the value of the numStateRoomTypes property.
     * 
     */
    public int getNumStateRoomTypes() {
        return numStateRoomTypes;
    }

    /**
     * Sets the value of the numStateRoomTypes property.
     * 
     */
    public void setNumStateRoomTypes(int value) {
        this.numStateRoomTypes = value;
    }

}
