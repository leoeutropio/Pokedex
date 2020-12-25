package com.leoeutropio.pokedex.retrofit

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterfaceUser {

//    //--------------------------------------------PUT---------------------------------------------//
//
//    @PUT("cliente/perfil-alterar-senha")
//    Call<AlterarSenhaRequest> editarSenha(@Body Map<String, String> param);
//
//    @PUT("cliente/perfil-editar")
//    Call<AlterarDadosRequest> alterarDados(@Body CadastroModel cadastroModel);
//
//    @PUT("cliente/pedido-carrinho-concluir-compra")
//    Call<ConfirmarPedidoRequest> confirmarPedido(@Body Map<String,String> codigo);
//
//    @PUT("cliente/pedido-confirmar-codigo")
//    Call<ConfirmarPedidoVendedorRequest> confirmarPedidoVendedor(@Body Map<String,String> codigo);
//
//    //-------------------------------------------POST----------------------------------------------//
//
//    @POST("cliente/login")
//    Call<LoginRequest> login(@Body Map<String, String> param);
//
//    @Multipart
//    @POST("utils/uploadTmp")
//    Call<UploadImagemRequest> uploadTemp(@Part MultipartBody.Part upload, @Part("extensao") RequestBody extensao);
//
//    @POST("cliente/cadastro")
//    Call<CadastroRequest> cadastrar(@Body CadastroModel cadastroModel);
//
//    @POST("cliente/pedido-avaliar-empresa")
//    Call<AvaliarEmpresaRequest> avaliarEmpresa(@Body Map<String, String> param);
//
//    @POST("cliente/pedido-avaliar-produto")
//    Call<AvaliarProdutoRequest> avaliarProduto(@Body Map<String, String> param);
//
//    @POST("cliente/pedido-carrinho-adicionar")
//    Call<AdicionarProdutoCarrinhoRequest> adicionarProdutoCarrinho(@Body Map<String, Integer> param);
//
//    @POST("cliente/esqueceu-senha")
//    Call<RecuperarSenhaRequest> recuperarSenha(@Body Map<String, String> param);
//
//    @POST("cliente/produtos-buscar")
//    Call<BuscarProdutosRequest> getProdutosDigitar(@Body Map<String, String> param);
//
//    @POST("cliente/realizar-aposta")
//    Call<RealizarApostaRequest> realizarAposta(@Body Map<String, String> param);
//
//    @POST("cliente/produtos-buscar?limit=10")
//    Call<BuscaGeralRequest> buscaGeral(@Body Map<String, Object> param,@Query("page") int page);
//
//    @POST("cliente/fale-conosco")
//    Call<FaleConoscoRequest> faleConosco(@Body Map<String, String> param);
//
//    @POST("cliente/salvar-indicacao")
//    Call<CodigoDeIndicacaoRequest> codigoIndicacao(@Body Map<String, String> param);
//
//    //------------------------------------------GET-----------------------------------------------//
//
//    @GET("cliente/categorias?")
//    Call<GetCategoriasRequest> getCategorias();
//
//    @GET("cliente/subcategorias/{id}?")
//    Call<GetSubcategoriasRequest> getSubCategorias(@Path("id")String id);
//
//    @GET("cliente/pedidos-carrinho")
//    Call<VisualizarItensCarrinhoRequest> getCarrinhoProduto();
//
//    @GET("cliente/pedido-carrinho-detalhes/{id}")
//    Call<VisualizarItemCarrinhoDetalheRequest> getCarrinhoProdutoDetalhe(@Path("id") int id);
//
//    @GET("cliente/produtos")
//    Call<BuscarProdutosRequest> getProdutos();
//
//    @GET("cliente/produto/{id}")
//    Call<BuscarProdutosDetalhesRequest> getProdutosDetalhes(@Path("id") int id);
//
//    @GET("cliente/produto/avaliacao-resumo/{id}")
//    Call<AvaliacoesDoProdutoRequest> getProdutosAvaliacoes(@Path("id") int id);
//
//    @GET("cliente/produto/avaliacao-comentarios/{id}")
//    Call<AvaliacaoComentarioRequest> getAvaliacoesComentarios(@Path("id") int id);
//
//    @GET("cliente/perfil")
//    Call<GetPerfilRequest> getPerfil();
//
//    @GET("cliente/pedidos?limit=20&desc=true")
//    Call<HistoricoPedidosRequest> getHistoricoPedidos(@Query("page") int page);
//
//    @GET("cliente/pedido-detalhes/{id}")
//    Call<HistoricoPedidoDetalheRequest> getHistoricoPedidosDetalhe(@Path("id") int id);
//
//    @GET("utils/estados")
//    Call<GetEstadosRequest> getEstados();
//
//    @GET("utils/estados/{id}")
//    Call<GetCidadesRequest> getCidades(@Path("id") int id);
//
//    @GET("cliente/empresas/categoria/{id}?limit=10")
//    Call<EmpresasCategoriaRequest> getEmpresasPorCategoria(@Path("id") String id, @Query("raio") String raio, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("page") int page);
//
//    @GET("cliente/empresa/{id}")
//    Call<EmpresaDetalheRequest> getEmpresaDetalhe(@Path("id") String id);
//
//    @GET("cliente/proximo-sorteio")
//    Call<ProximoSorteioRequest> getProximoSorteio();
//
//    @GET("cliente/meus-sorteios?desc=true")
//    Call<MeusJogosRequest> getMeusJogos();
//
//    @GET("cliente/sorteio-detalhes/{id}")
//    Call<MeusJogosDetalheRequest> getMeusJogosDetalhe(@Path("id")String id);
//
//    @GET("cliente/premios")
//    Call<ListarPremiosRequest> getPremios();
//
//    @GET("cliente/empresa-cat-e-prod")
//    Call<ProdutosLojaRequest> getProdutosEmpresa(@Query("categoria_id") String idcategoria, @Query("empresa_id") String idempresa);
//
//    @GET("cliente/instrucoes-uso")
//    Call<InstrucoesDeUsoRequest> getInstrucoesDeUso();
//
//    @GET("cliente/banner?")
//    Call<GetBannersRequest> getBanners(@Query("categoria_id") String idcategoria, @Query("limit") String limit,@Query("order") String order);
//
//    @GET("cliente/produtos-destaque")
//    Call<BuscaProdutosDestaqueRequest> getProdutosDestaque(@Query("categoria_id") String idcategoria, @Query("subcategoria_id") String subcategoria_id, @Query("por") String por);
//
//    @GET("cliente/produtos-buscar-sugestao?")
//    Call<BuscaSugestaoRequest> getBuscaSugestao(@Query("por") String por);
//
//    @GET("cliente/empresa-cat-e-prod-list?limit=10")
//    Call<ProdutosEstabelecimentoRequest> getProdutosEstabelecimento(@Query("categoria_id") String categoriaId, @Query("empresa_id") String empresaId, @Query("page") int page);
//
//    @GET("{cep}/json/ ")
//    Call<CepResponse> cep(@Path("cep") String cep);
//
//    //------------------------------------------DELETE-----------------------------------------------//
//
//    @DELETE("cliente/pedido-carrinho-remover-pedido/{codigo}")
//    Call<DeletarMeusPedidosRequest> deletarMeusPedidos(@Path("codigo") String codigo);


}