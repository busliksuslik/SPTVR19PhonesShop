class ProductModule{
    printProducts(){
        document.getElementById("content").innerHTML = `
            <input type="text" placeholder="name" id="login" name="login" value=""><br>
            <input type="password" placeholder="Password" id="password" name = "password" value=""><br>
            <input id="btnEnter" type="button" value="Enter"><br>
            <a id="registration-link" href="#registration">registration</a>`;
    }
    async getListProducts() {
    let response = await fetch('listProductsJson', {
      method: 'GET',
      headers: { 'Content-Type': 'application/json;charset:utf-8' }
    });
    if (response.ok) {
      let result = await response.json();
      return result;
    } else {
      document.getElementById('info').innerHTML = 'Ошибка сервера';
      return null;
    }
  }
}

let productModule = new ProductModule();
export {productModule};

