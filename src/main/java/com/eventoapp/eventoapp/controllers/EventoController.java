package com.eventoapp.eventoapp.controllers;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventosapp.eventoapp.repository.ConvidadoRepository;
import com.eventosapp.eventoapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;

    @RequestMapping(value="/cadastrarEvento", method= RequestMethod.GET)
    public String form(){
        return "evento/formEvento";
    }

    @RequestMapping(value="/cadastrarEvento", method= RequestMethod.POST)
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifiqe os Campos");
            return "redirect:/cadastrarEvento";
        }

        er.save(evento);

        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @RequestMapping( value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado> convidados = cr.findByEvento(evento);
        mv.addObject("convidados", convidados);

        return mv;
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo){

        Evento evento = er.findByCodigo(codigo);
        er.delete(evento);

        return "redirect:/eventos";


    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado,
                                     BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/{codigo}";
        }

        Evento e = er.findByCodigo(codigo);
        convidado.setEvento(e);
        cr.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado Incluído com Sucesso");
        return "redirect:/{codigo}";
    }

    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg){

        Convidado convidado = cr.findByRg(rg);
        cr.delete(convidado);

        Evento evento = convidado.getEvento();
        long codigoEvento = evento.getCodigo();

        return "redirect:/"+codigoEvento;
    }

}
