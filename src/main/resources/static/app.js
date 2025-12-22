// при пустом LHV запрашиваем /api/compositions/search (контроллер возвращает все записи)
const searchBtn = document.getElementById('searchBtn');
const calcBtn = document.getElementById('calcBtn');
const lhvInput = document.getElementById('lhv');
const tolInput = document.getElementById('tolerance');
const tbody = document.querySelector('#resultTable tbody');
const status = document.getElementById('status');
const errorBox = document.getElementById('error');

function setStatus(text) { status.textContent = text || ''; }
function setError(text) { errorBox.textContent = text || ''; }

function renderRows(items) {
  tbody.innerHTML = '';
  if (!items || items.length === 0) {
    tbody.innerHTML = '<tr><td colspan="15" style="text-align:center;color:#666">Ничего не найдено</td></tr>';
    return;
  }
  for (const c of items) {
    const tr = document.createElement('tr');
    // все концентрации в том порядке, как в таблице
    const cells = [
      c.id ?? '',
      c.concentrationOfSovtolAndWWT ?? '',
      c.concentrationOfSovtol ?? '',
      c.concentrationOfWWT ?? '',
      c.concentrationOfAcetone ?? '',
      c.concentrationOfFe ?? '',
      c.concentrationOfMn ?? '',
      c.concentrationOfSi ?? '',
      c.concentrationOfCa ?? '',
      c.concentrationOfMg ?? '',
      c.concentrationOfAl ?? '',
      c.concentrationOfCu ?? '',
      c.concentrationOfWater ?? '',
      c.concentrationOfNonBurningElements ?? '',
      c.lowerHeatingValueOfComposition ?? ''
    ];
    for (const value of cells) {
      const td = document.createElement('td');
      td.textContent = (typeof value === 'number') ? Number(value).toFixed(4) : value;
      tr.appendChild(td);
    }
    tbody.appendChild(tr);
  }
}

function showEmptyPlaceholder() {
  tbody.innerHTML = '<tr><td colspan="15" style="text-align:center;color:#666">Таблица пуста — введите LHV и нажмите «Поиск»</td></tr>';
  setStatus('');
  setError('');
}

async function search() {
  setError('');
  setStatus('Загрузка...');
  const lhv = (lhvInput.value || '').trim();
  const tol = tolInput.value || '0.05';

  // если LHV не указан — запрашиваем все записи через /api/compositions/search
  const url = lhv === ''
    ? '/api/compositions/search'
    : `/api/compositions/search?lowerHeatingValue=${encodeURIComponent(lhv)}&tolerance=${encodeURIComponent(tol)}`;

  try {
    const resp = await fetch(url);
    if (!resp.ok) throw new Error(`Ошибка сервера: ${resp.status}`);
    const data = await resp.json();
    renderRows(data);
    setStatus(`Найдено ${data.length} строк`);
  } catch (e) {
    setError(e.message);
    renderRows([]);
    setStatus('');
  }
}

async function calculateAll() {
  setError('');
  setStatus('Запуск расчёта...');
  try {
    const resp = await fetch('/api/compositions/calculate', { method: 'POST' });
    if (!resp.ok) throw new Error(`Ошибка при расчёте: ${resp.status}`);
    setStatus('Расчёт завершён. Обновляю результаты...');
    await search();
  } catch (e) {
    setError(e.message);
    setStatus('');
  }
}

searchBtn.addEventListener('click', search);
calcBtn.addEventListener('click', () => {
  if (!confirm('Запустить полный расчёт и перезаписать таблицу в БД?')) return;
  calculateAll();
});

window.addEventListener('DOMContentLoaded', () => {
  showEmptyPlaceholder(); // показываем пустую таблицу при загрузке
});