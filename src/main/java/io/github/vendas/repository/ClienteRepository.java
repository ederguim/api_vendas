package io.github.vendas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.vendas.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	void saveAndFlush(Optional<Cliente> _cliente);

//	List<Cliente> findByNomeLike(String nome);
//
//	List<Cliente> findByNomeLikeOrIdOrderById(String nome, Integer id);
//
//	boolean existsByNome(String nome);
//
//	@Query(value = "select c from cliente c where c.nome like :nome")
//	List<Cliente> listarPorNomeHql(@Param("nome") String nome);
//
//	@Query(value = "select * from cliente c where c.nome like '%:nome'", nativeQuery = true)
//	List<Cliente> listarPorNomeSql(@Param("nome") String nome);
//
//	void deleteByNome(String nome);
//
//	@Query("delete from Cliente c where c.nome = :nome")
//	@Modifying
//	void deleteByNomeHql(String nome);
//
//	@Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
//	Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
