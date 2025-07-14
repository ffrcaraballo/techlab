// JavaScript para el frontend
function mostrarProductos() {
  document.getElementById("seccion-formulario").style.display = "none";
  document.getElementById("seccion-productos").style.display = "block";

  fetch("/api/productos")
    .then(res => res.json())
    .then(data => {
      const tbody = document.querySelector("#tabla-productos tbody");
      tbody.innerHTML = "";
      data.forEach(p => {
        tbody.innerHTML += `<tr>
          <td>${p.id}</td><td>${p.nombre}</td><td>${p.precio}</td>
          <td>${p.categoria}</td><td>${p.stock}</td>
        </tr>`;
      });
    });
}

function mostrarFormulario() {
  document.getElementById("seccion-productos").style.display = "none";
  document.getElementById("seccion-formulario").style.display = "block";
}

function agregarProducto(event) {
  event.preventDefault();
  const form = document.getElementById("form-agregar");
  const producto = {
    nombre: form.nombre.value,
    descripcion: form.descripcion.value,
    precio: parseFloat(form.precio.value),
    categoria: form.categoria.value,
    imagenUrl: form.imagenUrl.value,
    stock: parseInt(form.stock.value)
  };

  fetch("/api/productos", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(producto)
  }).then(res => {
    if (res.ok) {
      form.reset();
      document.getElementById("resultado-formulario").textContent = "✅ Producto agregado";
    } else {
      document.getElementById("resultado-formulario").textContent = "❌ Error al agregar producto";
    }
  });
}
