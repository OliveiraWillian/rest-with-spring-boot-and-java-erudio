package br.com.oliveirawillian.integrationtests.dto.wrappers.xml;

import br.com.oliveirawillian.integrationtests.dto.BooksDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PageModelBooks implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "content")
    private List<BooksDTO> content;

    public PageModelBooks() {
    }

    public List<BooksDTO> getContent() {
        return content;
    }

    public void setContent(List<BooksDTO> content) {
        this.content = content;
    }
}
