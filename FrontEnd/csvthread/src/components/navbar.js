function Navbar(){

    return(
        <nav class="navbar navbar-expand-lg bg-dark border-bottom border-body" data-bs-theme="dark">
        <div class="container-fluid">
           <div><i class="fa-solid fa-headphones-simple navbar-brand"></i></div> 
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
          data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" 
          aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
              <a class="nav-link active" aria-current="page" href="/">Inicio</a>
              <a class="nav-link" href="/operativo">Operativo</a>
              <a class="nav-link" href="/reporte">Reporte</a>
            </div>
          </div>
        </div>
      </nav>
    );
}

export default Navbar;