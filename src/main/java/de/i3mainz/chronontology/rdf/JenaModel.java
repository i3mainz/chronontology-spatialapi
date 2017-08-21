package de.i3mainz.chronontology.rdf;

import com.github.jsonldjava.jena.JenaJSONLD;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import de.i3mainz.chronontology.errorlog.JenaModelException;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * CLASS for set up a JenaModel graph and export it
 *
 */
public class JenaModel {

    private Model model = null;

    public void setModel(Model model) {
        this.model = model;
    }
    
    public void setModelLiteral(String subject, String predicate, String object) throws JenaModelException {
        try {
            Resource s = model.createResource(subject);
            Property p = model.createProperty(predicate);
            Literal o = model.createLiteral(object);
            model.add(s, p, o);
        } catch (Exception e) {
            throw new JenaModelException(e.getMessage());
        }
    }

    public void setModelLiteralLanguage(String subject, String predicate, String object, String lang) throws JenaModelException {
        try {
            Resource s = model.createResource(subject);
            Property p = model.createProperty(predicate);
            Literal o = model.createLiteral(object, lang);
            model.add(s, p, o);
        } catch (Exception e) {
            throw new JenaModelException(e.getMessage());
        }
    }

    public void setModelURI(String subject, String predicate, String object) throws JenaModelException {
        try {
            Resource s = model.createResource(subject);
            Property p = model.createProperty(predicate);
            Resource o = model.createResource(object);
            model.add(s, p, o);
        } catch (Exception e) {
            throw new JenaModelException(e.getMessage());
        }
    }

    public void setModelTriple(String subject, String predicate, String object) throws JenaModelException {
        try {
            if (object.startsWith("http://") || object.contains("mailto")) {
                setModelURI(subject, predicate, object);
            } else if (object.contains("@")) {
                String literalLanguage[] = object.split("@");
                setModelLiteralLanguage(subject, predicate, literalLanguage[0].replaceAll("\"", ""), literalLanguage[1]);
            } else {
                setModelLiteral(subject, predicate, object.replaceAll("\"", ""));
            }
        } catch (Exception e) {
            throw new JenaModelException(e.getMessage());
        }
    }

    public Model getModelObject() {
        return model;
    }
    
    public String getModel() throws JenaModelException {
        try {
            JenaJSONLD.init();
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            model.write(o, "RDF/XML");
            return o.toString("UTF-8");
        } catch (Exception e) {
            throw new JenaModelException(e.getMessage());
        }
    }

    public String getModel(String format) throws UnsupportedEncodingException, JenaModelException {
        // https://jena.apache.org/documentation/io/rdf-output.html#jena_model_write_formats
        try {
            JenaJSONLD.init();
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            model.write(o, format);
            return o.toString("UTF-8");
        } catch (Exception e) {
            throw new JenaModelException(e.getMessage());
        }
    }

}
