<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>??m ng??c 180 giây</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      text-align: center;
      padding: 50px;
      background: #f0f0f0;
    }
    #countdown {
      font-size: 48px;
      font-weight: bold;
      color: #333;
    }
  </style>
</head>
<body>
  <h1>??m ng??c 180 giây</h1>
  <div id="countdown">03:00</div>

  <script>
    let timeLeft = 180; // th?i gian ??m ng??c tính b?ng giây

    function updateCountdown() {
      let minutes = Math.floor(timeLeft / 60);
      let seconds = timeLeft % 60;
      
      // Format d?ng MM:SS
      let display = 
        (minutes < 10 ? '0' + minutes : minutes) + ':' + 
        (seconds < 10 ? '0' + seconds : seconds);

      document.getElementById('countdown').textContent = display;

      if (timeLeft > 0) {
        timeLeft--;
      } else {
        clearInterval(timer);
        alert("H?t th?i gian!");
      }
    }

    updateCountdown(); // hi?n th? l?n ??u ngay khi load
    let timer = setInterval(updateCountdown, 1000);
  </script>
</body>
</html>