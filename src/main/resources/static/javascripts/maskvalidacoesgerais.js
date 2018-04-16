    $(document).ready(function () { 
        $('.cnpj').mask('00.000.000/0000-00', {reverse: false});
        $('.cpf').mask('000.000.000-00', {reverse: false});
        $('.phone').mask('0000-0000', {reverse: false});
        
        $(".datepicker").mask("00/00/0000");
        
   	 	$("#limpar").click(function(){
   		 	$("input:not(#limpar)").val('');
   	 	});
        
    });
    
    
     