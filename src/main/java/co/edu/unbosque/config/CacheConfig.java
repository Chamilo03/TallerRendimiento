package co.edu.unbosque.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration 
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // Configuración del builder de Caffeine
        Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(10000);

        // Caches que vas a usar en toda la aplicación
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
            "products",               // búsqueda general de productos
            "productDetails",         // detalles de producto por ID
            "topSelling",             // productos más vendidos
            "categorias",             // listado de categorías
            "categoria",              // categoría por ID
            "categoriasPorNombre",    // búsqueda de categoría por nombre
            "inventarioList",         // todo el inventario
            "inventario",             // inventario por ID
            "inventarioPorProducto",  // inventario por producto ID
            "productos",              // todos los productos
            "producto",               // producto por ID
            "productosPorNombre",     // búsqueda por nombre
            "productosPorCategoria",  // búsqueda por categoría
            "productosPorRangoPrecio", // búsqueda por rango de precio
            "topSelling",
            "transacciones",
            "transaccion",
            "transaccionesPorProducto",
            "transaccionesPorTipo",
            "usuarios",
            "usuario",
            "usuariosPorRol",
            "usuarioPorCorreo"
        );

        // Aplica la configuración de Caffeine al manager
        cacheManager.setCaffeine(caffeineBuilder);

        return cacheManager;
    }
}
