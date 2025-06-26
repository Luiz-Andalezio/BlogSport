window.toggleLike = function(postId) {
  console.log(`Iniciando toggleLike para o postId: ${postId}`); // Log inicial

  fetch(`/likes/post/${postId}/toggle`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
  })
    .then(response => {
      console.log(`Resposta recebida do servidor para postId: ${postId}`, response); // Log da resposta
      if (!response.ok) {
        throw new Error(`Erro ao dar like no postId: ${postId} - Status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      console.log(`Dados recebidos do servidor para postId: ${postId}`, data); // Log dos dados recebidos
      const likeCountElement = document.getElementById(`like-count-${postId}`);
      if (likeCountElement) {
        likeCountElement.textContent = data.likeCount;
        console.log(`Número de likes atualizado para postId: ${postId} - Novo valor: ${data.likeCount}`); // Log de sucesso
      } else {
        console.error(`Elemento like-count-${postId} não encontrado na página.`); // Log de erro
      }
    })
    .catch(error => {
      console.error(`Erro ao processar toggleLike para postId: ${postId}`, error); // Log de erro
    });
};