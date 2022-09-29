//package com.skt.search.listener;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class GoodsListener {
//    @Autowired
//
//    private GoodsHtmlService goodsHtmlService;
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "skt.GOODS.WEB.CREATE.QUEUE",durable = "true"),
//            exchange = @Exchange(
//                    value = "skt.item.exchange",
//                    ignoreDeclarationExceptions = "true",
//                    type = ExchangeTypes.TOPIC),
//            key = {"item.insert", "item.update"}))
//    public void listenCreate(Long id){
//        if(id==null){
//            return;
//        }
//        goodsHtmlService.asyncExcute(id);
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "skt.GOODS.WEB.DELETE.QUEUE",durable = "true"),
//            exchange = @Exchange(
//                    value = "skt.item.exchange",
//                    ignoreDeclarationExceptions = "true",
//                    type = ExchangeTypes.TOPIC),
//            key = {"item.delete"}))
//    public void listenDelete(Long id){
//        if(id==null){
//            return;
//        }
//        goodsHtmlService.deleteHtml(id);
//    }
//}
